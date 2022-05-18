package com.boos.stock.data.repository

import com.boos.stock.data.csv.CSVParser
import com.boos.stock.data.local.StockDatabase
import com.boos.stock.data.remote.StockApi
import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.domain.model.IntradayInfoModel
import com.boos.stock.domain.repository.StockRepository
import com.boos.stock.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import toCompanyInfo
import toCompanyListing
import toCompanyListingEntity
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val database: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListingModel>,
    private val intrDayParser: CSVParser<IntradayInfoModel>
) : StockRepository {

    override suspend fun getCompanyListings(
        query: String,
        fetchRemote: Boolean
    ): Flow<Resource<List<CompanyListingModel>>> {
        return flow {
            val dao = database.stockDao
            emit(Resource.Loading(isLoading = true))
            val localListings = dao.searchCompanyListings(query)
            emit(
                Resource.Success(
                    data = localListings.map { it.toCompanyListing() })
            )

            val isDbEmpty = query.isBlank() && localListings.isEmpty()
            val shouldUseCache = isDbEmpty.not() && fetchRemote.not()
            //val shouldUseCache = false

            if (shouldUseCache) {
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to IOException"))
                null

            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to HttpException"))
                null
            }
            remoteListings?.let { companyListings ->
                dao.clearCompanyListings()
                dao.insertCompanyListing(companyListings.map { it.toCompanyListingEntity() })
                emit(Resource.Success(dao.searchCompanyListings("").map { it.toCompanyListing() }))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getIntradayInfo(symbol: String): Flow<Resource<List<IntradayInfoModel>>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val intraDayInfoModel = try {
                val response = api.getIntradayInfo(symbol)
                intrDayParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to IOException"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to HttpException"))
                null
            }
            intraDayInfoModel?.let { emit(Resource.Success(it)) }
            emit(Resource.Loading(isLoading = false))
        }

    }

    override suspend fun getCompanyInfo(symbol: String): Flow<Resource<CompanyInfoModel>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            try {
                val response = api.getCompanyInfo(symbol)
                emit(Resource.Success(response.toCompanyInfo()))
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to IOException"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Couldn't load data due to HttpException"))
            }
            emit(Resource.Loading(isLoading = false))
        }
    }

    override suspend fun getFavorites(): List<CompanyListingModel> {
        return database.stockDao.getFavorites().map { it.toCompanyListing() }
    }

    override suspend fun updateCompanyListing(companyListingModel: CompanyListingModel) {
        try {
            val companyListingEntity = database.stockDao.findCompanyListing(
                companyListingModel.symbol,
                companyListingModel.name
            )
            database.stockDao.updateCompanyListing(companyListingEntity.copy(isFavorite = companyListingModel.isFavorite))
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error finding matching listing entity in database for model $companyListingModel")
        }
    }
}