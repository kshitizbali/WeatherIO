package com.kshitiz.weatherio.data.remote

import com.kshitiz.weatherio.BuildConfig
import com.kshitiz.weatherio.data.dto.WeatherCurrentDto
import com.kshitiz.weatherio.data.dto.WeatherMainDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Weather Api interface
 */
interface WeatherIOApi {
    /**
     * Weather forecast by city
     * @param city: A String representing a city.
     */
    @GET("forecast?&cnt=10&units=imperial&APPID=" + BuildConfig.API_KEY)
    suspend fun getWeatherByCity(
        @Query("q") city: String
    ): WeatherMainDto

    /**
     * Weather forecast of a location.
     * @param lat A string representing the latitude of a location.
     * @param lon A string representing the longitude of a location.
     */
    @GET("forecast?&cnt=10&units=imperial&appid=" + BuildConfig.API_KEY)
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherMainDto

    /**
     * Current weather data for a location.
     * @param lat A string representing the latitude of a location.
     * @param lon A string representing the longitude of a location.
     */
    @GET("weather?&units=imperial&appid=" + BuildConfig.API_KEY)
    suspend fun getCurrentWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): WeatherCurrentDto

    /**
     * Current weather for a city.
     * @param city: A string which represents a chosen city.
     */
    @GET("weather?&units=imperial&appid=" + BuildConfig.API_KEY)
    suspend fun getCurrentWeatherByCity(
        @Query("q") city: String
    ): WeatherCurrentDto
}