package com.kshitiz.weatherio.domain.repository

import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo
import com.kshitiz.weatherio.domain.model.WeatherInfo
import com.kshitiz.weatherio.domain.util.Resource

/**
 * WeatherRepository is the interface that provides the methods to fetch the weather data.
 */
interface WeatherRepository {
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>

    suspend fun getWeatherDataByCity(city: String): Resource<WeatherInfo>

    suspend fun getCurrentWeather(lat: Double, long: Double): Resource<CurrentWeatherInfo>

    suspend fun getCurrentWeatherByCity(city: String): Resource<CurrentWeatherInfo>
}