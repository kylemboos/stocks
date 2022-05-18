package com.boos.stock.ui.listings.presentation

import com.boos.stock.domain.model.CompanyListingModel

data class ListingsUiState(
    val companies: List<CompanyListingModel> = emptyList(),
    val isLoading : Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = ""
)

sealed class ListingUiEvent {
    object RefreshSwipe: ListingUiEvent()
    data class Search(val query: String): ListingUiEvent()
    data class FavoriteClicked(val companyListingModel: CompanyListingModel): ListingUiEvent()
}