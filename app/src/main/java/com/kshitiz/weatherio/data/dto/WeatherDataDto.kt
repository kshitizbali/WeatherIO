package com.kshitiz.weatherio.data.dto

import com.squareup.moshi.Json

data class WeatherDataDto(
    @field:Json(name = "dt")
    val timeStamp: Long,
    @field:Json(name = "main")
    val main: MainDto,
    @field:Json(name = "weather")
    val weather: List<WeatherDto>,
    @field:Json(name = "wind")
    val wind: WindDto,
    @field:Json(name = "dt_txt")
    val dateTime : String
)
