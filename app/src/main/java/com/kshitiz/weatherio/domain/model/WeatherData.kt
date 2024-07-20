package com.kshitiz.weatherio.domain.model

import java.time.LocalDateTime

data class WeatherData(
    val time: LocalDateTime,
    val temperatureFahren: Double,
    val pressure: Int,
    val windSpeed: Double,
    val humidity: Int,
    val weatherType: WeatherType
)
