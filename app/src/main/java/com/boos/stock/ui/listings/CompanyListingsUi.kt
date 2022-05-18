package com.boos.stock

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.ui.listings.presentation.CompanyItem
import com.boos.stock.ui.listings.presentation.ListingsUiState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun CompanyListingsUi(
    onListingClicked: (CompanyListingModel) -> Unit,
    state: ListingsUiState,
    onSearchValueChange: (String) -> Unit,
    onRefreshSwipe: () -> Unit,
    onFavoriteClicked: (CompanyListingModel) -> Unit
) {
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = state.isRefreshing)

    Column(Modifier.fillMaxSize()) {
        OutlinedTextField(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            value = state.searchQuery,
            onValueChange = {
                onSearchValueChange(it)
            },
            placeholder = { Text("Search...") },
            maxLines = 1,
            singleLine = true
        )
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            onRefreshSwipe()
        }) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(state.companies.size) { index ->
                    val company = state.companies[index]
                    CompanyItem(
                        companyListingModel = company,
                        onFavoriteClicked = onFavoriteClicked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onListingClicked(company)
                            }
                            .padding(16.dp)
                    )
                    if (index < state.companies.size) {
                        Divider(Modifier.padding(16.dp))
                    }
                }
            }
        }
    }
}