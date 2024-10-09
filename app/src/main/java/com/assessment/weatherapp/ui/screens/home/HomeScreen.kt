package com.assessment.weatherapp.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.assessment.weatherapp.ui.screens.home.HomeViewModel
import com.assessment.weatherapp.ui.screens.weather.WeatherScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi

// Home Screen
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(navController: NavHostController, homeViewModel: HomeViewModel) {
    val scrollStateScreen = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollStateScreen)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 30.dp,
                    start = 8.dp,
                    end = 8.dp,
                    bottom = 10.dp
                )
            ) {
                Button(
                    onClick = { navController.navigate("search") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .background(color = Color.Transparent, shape = RoundedCornerShape(12.dp))
                ) {
                    Text("Search cities here", color = Color.White)
                }

                // current location weather data on WeatherScreen
                val result = homeViewModel.currentLocationWeatherData.value
                WeatherScreen(result)

            }

        }

    }
}