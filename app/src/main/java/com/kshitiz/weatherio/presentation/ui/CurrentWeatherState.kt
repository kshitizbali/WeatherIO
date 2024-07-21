package com.kshitiz.weatherio.presentation.ui

import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo

/**
 * State data class for current weather.
 * @param weatherInfo Nullable WeatherInfo data class which contains the weather info.
 * @param isLoading Flag to indicate if the data is loading or not.
 * @param error Nullable string to indicate the error occurred fetching weather info.
 */
data class CurrentWeatherState(
    val weatherInfo: CurrentWeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
