package com.assessment.weatherapp.ui.screens.search

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherapp.data.repository.WeatherRepository
import com.assessment.weatherapp.ui.screens.home.HomeState
import com.assessment.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository, @ApplicationContext private val context: Context // Inject context for SharedPreferences
) : ViewModel() {

    val weatherData: MutableState<HomeState?> = mutableStateOf(null)
    val lastSearchedCity: MutableState<String> = mutableStateOf("")
    init {
        // Load the last searched city when ViewModel is initialized
        loadLastSearchedCity()
    }

    fun getWeatherData(cityName: String) = viewModelScope.launch {
        lastSearchedCity.value = cityName // Update the state for last searched city
        saveLastSearchedCity(cityName) // Save it to SharedPreferences

        when (val result = weatherRepository.getWeather(cityName)) {
            is NetworkResult.Loading -> {
                weatherData.value = HomeState(isLoading = true)

            }
            is NetworkResult.Success -> {
                result.data?.let {
                    weatherData.value = HomeState(data = result.data)
                }
            }
            is NetworkResult.Error -> {
                if (result.message == "404") {
                    weatherData.value = HomeState(error = "City Not Found")
                } else {
                    weatherData.value = HomeState(error = "Something went wrong!!!")
                }
            }
        }
    }

    // Save the last searched city using SharedPreferences
    private fun saveLastSearchedCity(cityName: String) {
        val sharedPref = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("last_searched_city", cityName)
            apply()
        }
    }


    // Load the last searched city and set the lastSearchedCity state
    private fun loadLastSearchedCity() {
        val sharedPref = context.getSharedPreferences("WeatherApp", Context.MODE_PRIVATE)
        val lastCity = sharedPref.getString("last_searched_city", "")
        if (!lastCity.isNullOrEmpty()) {
            lastSearchedCity.value = lastCity // Update state with last searched city
        }
    }

}
