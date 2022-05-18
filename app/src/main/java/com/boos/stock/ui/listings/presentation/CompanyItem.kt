package com.boos.stock.ui.listings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.boos.stock.domain.model.CompanyListingModel
import com.boos.stock.ui.theme.StocksTheme

@Composable
fun CompanyItem(
    companyListingModel: CompanyListingModel,
    onFavoriteClicked: (CompanyListingModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(Modifier.weight(1f)) {
            Text(
                text = companyListingModel.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colors.onBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = companyListingModel.symbol,
                color = MaterialTheme.colors.onBackground,
                fontStyle = FontStyle.Italic,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = "(${companyListingModel.exchange})",
                fontWeight = FontWeight.Light,
                color = MaterialTheme.colors.onBackground
            )
        }
        Spacer(Modifier.width(8.dp))

        val favoriteIcon =
            remember(companyListingModel.isFavorite) { if (companyListingModel.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder }
        Icon(
            imageVector = favoriteIcon,
            contentDescription = "Favorite",
            modifier = Modifier
                .size(30.dp)
                .clickable { onFavoriteClicked(companyListingModel) }
        )
        //Spacer(Modifier.width(8.dp))
    }
}

@Preview
@Composable
fun CompanyListingPreview() {
    StocksTheme {
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            var listingModel by remember {
                mutableStateOf(
                    CompanyListingModel(
                        symbol = "WDAY",
                        name = "Workday Inc.",
                        exchange = "NYSE"
                    )
                )
            }
            CompanyItem(
                companyListingModel = listingModel,
                onFavoriteClicked = {
                    listingModel = listingModel.copy(isFavorite = !it.isFavorite)
                }
            )
        }
    }
}