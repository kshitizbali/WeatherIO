package com.kshitiz.weatherio.presentation.ui

import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo

data class CurrentWeatherState(
    val weatherInfo: CurrentWeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
