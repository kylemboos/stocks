package com.boos.stock.ui.listings.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boos.stock.domain.repository.StockRepository
import com.boos.stock.util.Resource
import com.boos.stock.util.replace
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCH_DELAY = 500L

@HiltViewModel
class ListingsViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var uiState by mutableStateOf(ListingsUiState())
    private var searchJob: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(uiEvent: ListingUiEvent) {
        when (uiEvent) {
            is ListingUiEvent.RefreshSwipe -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is ListingUiEvent.Search -> {
                uiState = uiState.copy(searchQuery = uiEvent.query)
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(SEARCH_DELAY)
                    getCompanyListings()
                }
            }
            is ListingUiEvent.FavoriteClicked -> {
                val updatedListing = uiEvent.companyListingModel.copy(isFavorite = !uiEvent.companyListingModel.isFavorite)
                uiState = uiState.copy(
                    companies = uiState.companies.replace(
                        uiEvent.companyListingModel,
                        updatedListing
                    )
                )
                viewModelScope.launch {
                    repository.updateCompanyListing(updatedListing)
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = uiState.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ): Job {
        return viewModelScope.launch {
            repository
                .getCompanyListings(query = query, fetchRemote = fetchFromRemote)
                .collect { result ->
                    when (result) {
                        is Resource.Loading -> {
                            uiState = uiState.copy(isLoading = result.isLoading)
                        }
                        is Resource.Success -> {
                            result.data?.let {
                                uiState = uiState.copy(companies = it)
                            }
                        }
                        is Resource.Error -> {
                            Unit
                        }
                    }
                }
        }
    }
}