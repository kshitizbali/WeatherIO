package com.kshitiz.weatherio.domain.model

data class WeatherInfo(
    val weatherDataPerDay: Map<Int, List<WeatherData>>
)
