package com.kshitiz.weatherio.presentation.ui

import com.kshitiz.weatherio.domain.model.WeatherInfo

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
