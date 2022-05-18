package com.boos.stock.ui.details.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.domain.repository.StockRepository
import com.boos.stock.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val stockRepository: StockRepository
) : ViewModel() {

    //need a state
    var uiState by mutableStateOf(DetailsUiState())

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            val companyInfoFlow = async { stockRepository.getCompanyInfo(symbol) }
            val intraDayFlow = async { stockRepository.getIntradayInfo(symbol) }
            companyInfoFlow.await().collect { result ->
                uiState = when(result) {
                    is Resource.Success -> {
                        uiState.copy(
                            companyInfoModel = result.data ?: CompanyInfoModel()
                        )
                    }
                    is Resource.Loading -> {
                        uiState.copy(
                            isLoading = result.isLoading
                        )
                    }
                    else -> {
                        uiState.copy(error = result.message ?: "Error!")
                    }
                }
            }

            intraDayFlow.await().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        uiState = uiState.copy(
                            intradayInfoModel = result.data ?: emptyList()
                        )
                    }
                    is Resource.Loading -> {
                        uiState = uiState.copy(
                            isLoading = result.isLoading
                        )
                    }
                    else -> {
                        Unit //handle error
                    }
                }
            }
        }
    }


    fun handleUiEvent(uiEvent: DetailsUiEvent) {
        when (uiEvent) {

        }
    }
}