package com.kshitiz.weatherio.presentation

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kshitiz.weatherio.domain.UserPreferences
import com.kshitiz.weatherio.domain.location.LocationTracker
import com.kshitiz.weatherio.domain.repository.WeatherRepository
import com.kshitiz.weatherio.domain.util.Resource
import com.kshitiz.weatherio.presentation.ui.CurrentWeatherState
import com.kshitiz.weatherio.presentation.ui.WeatherState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

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


    fun loadWeatherInfo() {
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
    }

    fun loadWeatherInfoByCity(city: String) {

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
            }
        }
    }

    fun checkInternetAvailability(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCapabilities =
            connectivityManager.activeNetwork ?: return false

        val activeNetwork =
            connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
    }

    fun checkInternetAvailabilityAsync(context: Context, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val isConnected = withContext(Dispatchers.IO) {
                checkInternetAvailability(context)
            }
            onResult(isConnected)
        }
    }

    fun getLastLocation(): String {
        return preferences.getLastLocation()
    }

    fun saveLastLocation(location: String) {
        preferences.saveLastLocation(location)
    }
}