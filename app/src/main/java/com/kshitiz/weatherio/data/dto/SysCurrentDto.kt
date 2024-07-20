package com.kshitiz.weatherio.data.dto

import com.squareup.moshi.Json

data class SysCurrentDto(
    @field:Json(name = "sunrise")
    val sunrise: Long,
    @field:Json(name = "sunset")
    val sunset: Long,
)
