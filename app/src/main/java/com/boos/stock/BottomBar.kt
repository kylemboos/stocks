package com.boos.stock

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.boos.stock.navigation.destinations.CompanyListingScreenDestination
import com.boos.stock.navigation.destinations.Destination
import com.boos.stock.navigation.destinations.FavoritesScreenDestination
import com.ramcosta.composedestinations.spec.DirectionDestinationSpec

enum class BottomBarDestination(
    val direction: DirectionDestinationSpec,
    val label: String,
    val icon: ImageVector
) {
    Listings(
        direction = CompanyListingScreenDestination,
        label = "Company Listings",
        icon = Icons.Default.Home
    ),
    Favorites(
        direction = FavoritesScreenDestination,
        label = "Favorites",
        Icons.Default.Favorite
    )
}


@Composable
fun StockBottomBar(
    currentDestination: Destination,
    navigateToDestination: (BottomBarDestination) -> Unit
) {
    BottomNavigation(
        modifier = Modifier
    ) {

        BottomBarDestination.values().forEach { destination ->
            BottomNavigationItem(
                selected = currentDestination == destination.direction,
                label = { Text(destination.label) },
                onClick = { navigateToDestination(destination) },
                icon = { Icon(imageVector = destination.icon, contentDescription = destination.label) }
            )
        }
    }
}