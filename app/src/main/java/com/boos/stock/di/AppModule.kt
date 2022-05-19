package com.boos.stock.di

import android.content.Context
import androidx.room.Room
import com.boos.stock.data.local.StockDatabase
import com.boos.stock.data.remote.AuthApi
import com.boos.stock.data.remote.StockApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideAuthApi(): AuthApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()


        return Retrofit.Builder()
            .baseUrl(AuthApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockApi(): StockApi {

        val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideStockDatabase(@ApplicationContext context: Context): StockDatabase {
        return Room.databaseBuilder(
            context,
            StockDatabase::class.java,
            "stockdb.db"
        ).build()
    }
}