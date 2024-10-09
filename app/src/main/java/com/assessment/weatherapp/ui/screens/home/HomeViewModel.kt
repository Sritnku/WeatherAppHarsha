package com.assessment.weatherapp.ui.screens.home

import android.location.Location
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assessment.weatherapp.data.location.LocationTracker
import com.assessment.weatherapp.data.repository.WeatherRepository
import com.assessment.weatherapp.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// View Model for Home Screen
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val locationTracker: LocationTracker,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    val currentLocationWeatherData: MutableState<HomeState?> = mutableStateOf(null)

    private fun getCurrentWeatherData(latitude: Double, longitude: Double) = viewModelScope.launch {

        when (val result = weatherRepository.getWeatherByLocation(latitude, longitude)) {
            is NetworkResult.Loading -> {
                currentLocationWeatherData.value = HomeState(isLoading = true)

            }
            is NetworkResult.Success -> {
                result.data?.let {
                    currentLocationWeatherData.value = HomeState(data = result.data)
                }
            }
            is NetworkResult.Error -> {
                currentLocationWeatherData.value = HomeState(error = "Something went wrong")
            }
        }
    }

    var currentLocation by mutableStateOf<Location?>(null)
    fun getCurrentLocation() {
        viewModelScope.launch(Dispatchers.IO) {
            currentLocation = locationTracker.getCurrentLocation()
            if (currentLocation != null) {
                getCurrentWeatherData(currentLocation!!.latitude, currentLocation!!.longitude)
            } else {
                Log.e("Error", "Error")
            }
        }
    }


}
