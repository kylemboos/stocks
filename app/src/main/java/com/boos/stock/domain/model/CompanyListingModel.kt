package com.boos.stock.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CompanyListingModel(
    val symbol: String,
    val name: String,
    val exchange: String,
    val isFavorite: Boolean = false
): Parcelable