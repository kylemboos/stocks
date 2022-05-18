package com.boos.stock.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Update

@Dao
interface StockDao {

    @Insert(onConflict = REPLACE)
    suspend fun insertCompanyListing(
        companyListingEntity: List<CompanyListingEntity>
    )

    @Update
    suspend fun updateCompanyListing(
        companyListingEntity: CompanyListingEntity
    )

    @Query("""
        SELECT *
        FROM companylistingentity
        WHERE :symbol = symbol AND :name = name
        LIMIT 1
    """)
    suspend fun findCompanyListing(
        symbol: String,
        name: String
    ): CompanyListingEntity

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings()

    @Query("""
        SELECT *
        FROM companylistingentity
        WHERE isFavorite
    """)
    suspend fun getFavorites(): List<CompanyListingEntity>

    @Query("""
        SELECT *
        FROM companylistingentity
        WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
            UPPER(:query) == symbol
    """)
    suspend fun searchCompanyListings(query: String): List<CompanyListingEntity>
}