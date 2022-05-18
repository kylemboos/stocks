package com.boos.stock.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.boos.stock.CompanyListingsUi
import com.boos.stock.favorites.FavoritesUi
import com.boos.stock.favorites.FavoritesViewModel
import com.boos.stock.navigation.destinations.CompanyDetailScreenDestination
import com.boos.stock.ui.details.CompanyDetailsUi
import com.boos.stock.ui.details.presentation.DetailsViewModel
import com.boos.stock.ui.listings.presentation.ListingUiEvent
import com.boos.stock.ui.listings.presentation.ListingsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
@Destination(start = true)
fun CompanyListingScreen(
    navigator: DestinationsNavigator,
    viewModel: ListingsViewModel = hiltViewModel()

) {
    CompanyListingsUi(
        onListingClicked = {
            navigator.navigate(CompanyDetailScreenDestination(it.symbol))
        },
        state = viewModel.uiState,
        onSearchValueChange = {
            viewModel.onEvent(ListingUiEvent.Search(it))
        },
        onRefreshSwipe = { viewModel.onEvent(ListingUiEvent.RefreshSwipe) },
        onFavoriteClicked = { viewModel.onEvent(ListingUiEvent.FavoriteClicked(it)) },
    )
}

@Composable
@Destination
fun CompanyDetailScreen(
    symbol: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    CompanyDetailsUi(state = viewModel.uiState)
}

@Composable
@Destination
fun FavoritesScreen(
    navigator: DestinationsNavigator,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    FavoritesUi(state = viewModel.uiState)
}