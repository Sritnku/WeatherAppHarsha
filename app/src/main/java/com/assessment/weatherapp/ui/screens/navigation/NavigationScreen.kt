package com.assessment.weatherapp.ui.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.assessment.weatherapp.ui.screens.home.HomeScreen
import com.assessment.weatherapp.ui.screens.home.HomeViewModel
import com.assessment.weatherapp.ui.screens.search.SearchScreen

@Composable
fun NavigationView(homeViewModel: HomeViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController, homeViewModel)
        }
        composable("search") {
            SearchScreen(
                onHome = { navController.popBackStack() }
            )
        }
    }
}