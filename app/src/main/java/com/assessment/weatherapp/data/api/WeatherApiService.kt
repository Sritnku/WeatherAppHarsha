package com.assessment.weatherapp.data.api

import com.assessment.weatherapp.data.model.WeatherResponse
import com.assessment.weatherapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface WeatherApiService {

    // api fetch weather data based on city searched by user
    @GET("weather")
    suspend fun getWeather(
        @Query("q") cityName:String,
        @Query("appid") apiKey:String = Constants.API_KEY,
        @Query("units") units: String = Constants.UNITS,
    ): Response<WeatherResponse>


    // api to get weather details based on user current location
    @GET("weather")
    suspend fun getCurrentLocationWeather(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey:String = Constants.API_KEY,
        @Query("units") units: String = Constants.UNITS,
    ):Response<WeatherResponse>
}