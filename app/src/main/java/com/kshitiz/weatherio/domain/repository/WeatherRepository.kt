package com.kshitiz.weatherio.domain.repository

import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo
import com.kshitiz.weatherio.domain.model.WeatherInfo
import com.kshitiz.weatherio.domain.util.Resource

/**
 * WeatherRepository is the interface that provides the methods to fetch the weather data.
 */
interface WeatherRepository {
    /**
     * Weather forecast of a location.
     * @param lat A string representing the latitude of a location.
     * @param long A string representing the longitude of a location.
     */
    suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo>

    /**
     * Weather forecast by city
     * @param city: A String representing a city.
     */
    suspend fun getWeatherDataByCity(city: String): Resource<WeatherInfo>


    /**
     * Current weather data for a location.
     * @param lat A string representing the latitude of a location.
     * @param lon A string representing the longitude of a location.
     */
    suspend fun getCurrentWeather(lat: Double, long: Double): Resource<CurrentWeatherInfo>

    /**
     * Current weather for a city.
     * @param city: A string which represents a chosen city.
     */
    suspend fun getCurrentWeatherByCity(city: String): Resource<CurrentWeatherInfo>
}