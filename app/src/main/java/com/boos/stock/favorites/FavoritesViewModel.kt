package com.boos.stock.favorites

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boos.stock.domain.repository.StockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: StockRepository
) : ViewModel() {

    var uiState by mutableStateOf(FavoritesUiState())

    init {
        getFavorites()
    }

    private fun getFavorites() {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            uiState = uiState.copy(
                companies = repository.getFavorites()
            )
            uiState = uiState.copy(isLoading = false)
        }
    }
}