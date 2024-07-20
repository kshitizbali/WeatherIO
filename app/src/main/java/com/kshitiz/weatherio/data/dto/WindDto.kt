package com.kshitiz.weatherio.data.dto

import com.squareup.moshi.Json

data class WindDto(
    @field:Json(name = "speed")
    val speed: Double,
    @field:Json(name = "degree")
    val degree: Int,
)
