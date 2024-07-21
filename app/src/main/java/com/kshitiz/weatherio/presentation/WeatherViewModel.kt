package com.kshitiz.weatherio.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kshitiz.weatherio.domain.UserPreferences
import com.kshitiz.weatherio.domain.location.LocationTracker
import com.kshitiz.weatherio.domain.repository.WeatherRepository
import com.kshitiz.weatherio.domain.util.Resource
import com.kshitiz.weatherio.domain.util.checkInternetAvailability
import com.kshitiz.weatherio.presentation.ui.CurrentWeatherState
import com.kshitiz.weatherio.presentation.ui.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

/**
 * Weather viewmodel contains the logic to fetch weather and update the UI states.
 * Injected Constructor params include:
 * @param repository Repository for api request.
 * @param locationTracker For fetching user location.
 * @param preferences User preferences to fetch and update the preferences for user location.
 */
@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val preferences: UserPreferences
) : ViewModel() {

    var currentState by mutableStateOf(CurrentWeatherState())
        internal set

    var state by mutableStateOf(WeatherState())
        internal set


    /**
     * Method to fetch weather info using the user's current location and then updating the UI state.
     * Handles both Success and Error states.
     */
    fun loadWeatherInfo() {
        try {
            viewModelScope.launch {
                currentState = currentState.copy(
                    isLoading = true,
                    error = null
                )
                state = state.copy(
                    isLoading = true,
                    error = null
                )
                locationTracker.getCurrentLocation()?.let { location ->
                    when (val result =
                        repository.getCurrentWeather(location.latitude, location.longitude)) {
                        is Resource.Success -> {
                            currentState = currentState.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            )
                            when (val resultForecast =
                                repository.getWeatherData(location.latitude, location.longitude)) {
                                is Resource.Success -> {
                                    state = state.copy(
                                        weatherInfo = resultForecast.data,
                                        isLoading = false,
                                        error = null
                                    )
                                }

                                is Resource.Error -> {
                                    state = state.copy(
                                        weatherInfo = null,
                                        isLoading = false,
                                        error = resultForecast.message
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            currentState = currentState.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                } ?: kotlin.run {
                    currentState = currentState.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                    state = state.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }
            }
        }catch (e: Exception) {
            if (e is CancellationException) throw e
            Log.e("Error fetching weather info ", e.message.toString())
        }

    }

    /**
     * Method to fetch weather info using the provided location/city  and then updating the UI state.
     * Handles both Success and Error states.
     * @param city A string which represents the city in USA selected by the user.
     */
    fun loadWeatherInfoByCity(city: String) {

        viewModelScope.launch {
            try {
                currentState = currentState.copy(
                    isLoading = true,
                    error = null
                )
                state = state.copy(
                    isLoading = true,
                    error = null
                )

                when (val result =
                    repository.getCurrentWeatherByCity(city = city)) {
                    is Resource.Success -> {
                        currentState = currentState.copy(
                            weatherInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                        when (val resultForecast =
                            repository.getWeatherDataByCity(city = city)) {
                            is Resource.Success -> {
                                state = state.copy(
                                    weatherInfo = resultForecast.data,
                                    isLoading = false,
                                    error = null
                                )
                            }

                            is Resource.Error -> {
                                state = state.copy(
                                    weatherInfo = null,
                                    isLoading = false,
                                    error = resultForecast.message
                                )
                            }
                        }
                    }

                    is Resource.Error -> {
                        currentState = currentState.copy(
                            weatherInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }

                /*locationTracker.getCurrentLocation()?.let { location ->
                    when (val result =
                        repository.getCurrentWeatherByCity(city = city)) {
                        is Resource.Success -> {
                            currentState = currentState.copy(
                                weatherInfo = result.data,
                                isLoading = false,
                                error = null
                            )
                            when (val resultForecast =
                                repository.getWeatherDataByCity(city = city)) {
                                is Resource.Success -> {
                                    state = state.copy(
                                        weatherInfo = resultForecast.data,
                                        isLoading = false,
                                        error = null
                                    )
                                }

                                is Resource.Error -> {
                                    state = state.copy(
                                        weatherInfo = null,
                                        isLoading = false,
                                        error = resultForecast.message
                                    )
                                }
                            }
                        }

                        is Resource.Error -> {
                            currentState = currentState.copy(
                                weatherInfo = null,
                                isLoading = false,
                                error = result.message
                            )
                        }
                    }
                } ?: kotlin.run {
                    currentState = currentState.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                    state = state.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }*/
            }catch (e: Exception) {
                if (e is CancellationException) throw e
                Log.e("Error fetching weather info by city", e.message.toString())
            }

        }
    }

    /**
     * Fetches the last location saved location from the encrypted preferences if empty then provides default
     * value (Palo Alto).
     */
    fun getLastLocation(): String? {
        return preferences.getLastLocation()
    }


    /**
     * Saves the last location selected to the encrypted shared preferences.
     */
    fun saveLastLocation(location: String) {
        preferences.saveLastLocation(location)
    }
}