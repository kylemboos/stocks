package com.boos.stock.ui.details.presentation

import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.domain.model.IntradayInfoModel

data class DetailsUiState(
    val isLoading: Boolean = false,
    val companyInfoModel: CompanyInfoModel = CompanyInfoModel(),
    val intradayInfoModel: List<IntradayInfoModel> = emptyList(),
    val error: String = ""
)

sealed class DetailsUiEvent {

}