package com.boos.stock

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.boos.stock.navigation.NavGraphs
import com.boos.stock.navigation.destinations.Destination
import com.boos.stock.navigation.startDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigateTo

@Composable
fun StockScaffold(navController: NavHostController) {
    val currentDestination: Destination =
        navController.currentDestination as? Destination ?: NavGraphs.root.startDestination
    val navigate: (BottomBarDestination) -> Unit = { destination ->
        navController.navigateTo(destination.direction) {
            launchSingleTop = true
        }
    }

    Scaffold(
        backgroundColor = MaterialTheme.colors.background,
        bottomBar = { StockBottomBar(currentDestination = currentDestination, navigate) }
    ) { paddingValues ->
        DestinationsNavHost(navGraph = NavGraphs.root, navController = navController)
    }
}