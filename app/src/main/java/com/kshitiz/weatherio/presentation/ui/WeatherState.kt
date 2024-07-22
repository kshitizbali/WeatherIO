package com.kshitiz.weatherio.presentation.ui

import com.kshitiz.weatherio.domain.model.WeatherInfo

/**
 * State class containing the weather  data of a location.
 */
data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
