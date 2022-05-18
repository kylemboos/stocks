package com.boos.stock.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boos.stock.domain.model.CompanyInfoModel
import com.boos.stock.ui.details.presentation.DetailsUiState
import com.boos.stock.ui.theme.StocksTheme

@Composable
fun CompanyDetailsUi(
    state: DetailsUiState,
) {
    StocksTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            if (state.error.isEmpty() && state.isLoading.not()) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    CompanyInfo(companyInfo = state.companyInfoModel)
                    if (state.intradayInfoModel.isNotEmpty()) {
                        Spacer(Modifier.height(8.dp))
                        Text(text = "Market Summary")
                        Spacer(Modifier.height(16.dp))
                        StockChart(
                            infoList = state.intradayInfoModel,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                                .align(CenterHorizontally)
                        )
                    }
                }
            }
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Center) {
                if(state.isLoading) {
                    CircularProgressIndicator()
                } else if(state.error.isNotBlank()) {
                    Text(
                        text = state.error,
                        color = MaterialTheme.colors.error
                    )
                }
            }

        }
    }
}

@Composable
private fun CompanyInfo(companyInfo: CompanyInfoModel) {
    Text(
        text = companyInfo.name,
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = companyInfo.symbol,
        fontStyle = FontStyle.Italic,
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(Modifier.height(8.dp))

    Divider(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    )
    Spacer(Modifier.height(8.dp))

    Text(
        text = "Industry: ${companyInfo.industry}",
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis
    )
    Spacer(Modifier.height(8.dp))
    Text(
        text = "Country: ${companyInfo.country}",
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        overflow = TextOverflow.Ellipsis
    )
    Spacer(Modifier.height(8.dp))

    Divider(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp)
    )
    Spacer(Modifier.height(8.dp))

    Text(
        text = companyInfo.description,
        fontSize = 12.sp,
        modifier = Modifier.fillMaxWidth(),
    )
    Spacer(Modifier.height(8.dp))

}