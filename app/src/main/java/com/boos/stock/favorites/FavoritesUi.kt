package com.boos.stock.favorites

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.ui.listings.presentation.CompanyItem
import com.boos.stock.ui.theme.StocksTheme

@Composable
fun FavoritesUi(state: FavoritesUiState) {
    StocksTheme {
        if(state.isLoading) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            FavoritesList(companies = state.companies)
        }
    }

}

@Composable
internal fun FavoritesList(companies: List<CompanyListingModel>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(companies.size) { index ->
            val company = companies[index]
            CompanyItem(
                companyListingModel = company,
                onFavoriteClicked = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            if (index < companies.size) {
                Divider(Modifier.padding(16.dp))
            }
        }
    }
}