package com.kshitiz.weatherio.domain.model

/**
 * data class for WeatherInfo day wise.
 */
data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>
)
