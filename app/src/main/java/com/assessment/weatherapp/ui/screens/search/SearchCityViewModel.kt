package com.assessment.weatherapp.ui.screens.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherapp.data.repository.WeatherRepository
import com.assessment.weatherapp.ui.screens.home.HomeState
import com.assessment.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchCityViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val weatherData: MutableState<HomeState?> = mutableStateOf(null)

    fun getWeatherData(cityName: String) = viewModelScope.launch {

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

}
