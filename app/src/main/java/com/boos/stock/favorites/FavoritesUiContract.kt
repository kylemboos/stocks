package com.boos.stock.favorites

import com.boos.stock.domain.model.CompanyListingModel

data class FavoritesUiState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading: Boolean = true
)
