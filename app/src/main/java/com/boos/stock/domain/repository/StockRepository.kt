package com.boos.stock.domain.repository

import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.domain.model.IntradayInfoModel
import com.boos.stock.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        query: String,
        fetchRemote: Boolean = true
        ): Flow<Resource<List<CompanyListingModel>>>

    suspend fun getIntradayInfo(
        symbol: String
    ): Flow<Resource<List<IntradayInfoModel>>>

    suspend fun getCompanyInfo(
        symbol: String
    ): Flow<Resource<CompanyInfoModel>>

    suspend fun getFavorites(): List<CompanyListingModel>

    suspend fun updateCompanyListing(
        companyListingModel: CompanyListingModel
    )
}