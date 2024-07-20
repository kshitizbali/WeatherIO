package com.kshitiz.weatherio.data.dto

import com.squareup.moshi.Json

data class WeatherMainDto(
    @field:Json(name = "list")
    val weatherData: List<WeatherDataDto>,
    @field:Json(name = "city")
    val city: CityDto
)
