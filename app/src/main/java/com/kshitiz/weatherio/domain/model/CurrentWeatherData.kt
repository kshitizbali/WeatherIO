package com.kshitiz.weatherio.domain.model

import java.time.LocalDateTime

/**
 * CurrentWeatherData data class.
 */
data class CurrentWeatherData(
    val time: String,
    val temperatureFahren: Double,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val weatherType: WeatherType,
    val name: String
)
