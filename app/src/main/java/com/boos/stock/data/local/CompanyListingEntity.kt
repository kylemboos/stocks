package com.boos.stock.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CompanyListingEntity(
    val symbol: String,
    val name: String,
    val exchange: String,
    val isFavorite: Boolean,
    @PrimaryKey val id: Int? = null
)