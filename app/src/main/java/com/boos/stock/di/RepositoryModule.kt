package com.boos.stock.di

import com.boos.stock.data.auth.PreferenceDataStoreTokenStore
import com.boos.stock.data.csv.CSVParser
import com.boos.stock.data.csv.CompanyListingsParser
import com.boos.stock.data.csv.IntradayInfoParser
import com.boos.stock.data.repository.AuthRepositoryImpl
import com.boos.stock.data.repository.StockRepositoryImpl
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.domain.model.IntradayInfoModel
import com.boos.stock.domain.repository.AuthRepository
import com.boos.stock.domain.repository.StockRepository
import com.boos.stock.domain.repository.TokenStore
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(companyListingsParser: CompanyListingsParser): CSVParser<CompanyListingModel>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(intradayInfoParser: IntradayInfoParser): CSVParser<IntradayInfoModel>

    @Binds
    @Singleton
    abstract fun bindStockRepository(stockRepositoryImpl: StockRepositoryImpl): StockRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindTokenStore(tokenStore: PreferenceDataStoreTokenStore): TokenStore
}