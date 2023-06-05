package com.assessment.weatherapp.di

import android.app.Application
import com.assessment.weatherapp.data.api.WeatherApiService
import com.assessment.weatherapp.data.repository.WeatherRepository
import com.assessment.weatherapp.utils.Constants.BASE_URL
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideWeatherApiService(): WeatherApiService {

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.addNetworkInterceptor(logging)
        val okHttpClient = httpClient.build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(WeatherApiService::class.java)
    }


    @Provides
    fun provideWeatherRepository(weatherApiService: WeatherApiService): WeatherRepository {
        return WeatherRepository(weatherApiService)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(application: Application): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)



}