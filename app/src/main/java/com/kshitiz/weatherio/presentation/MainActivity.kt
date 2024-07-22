package com.kshitiz.weatherio.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kshitiz.weatherio.domain.util.checkInternetAvailability
import com.kshitiz.weatherio.presentation.ui.WeatherCard
import com.kshitiz.weatherio.presentation.ui.WeatherForecast
import com.kshitiz.weatherio.presentation.ui.WeatherSearchBar
import com.kshitiz.weatherio.presentation.ui.WeatherViewModel
import com.kshitiz.weatherio.presentation.ui.theme.Purple40
import com.kshitiz.weatherio.presentation.ui.theme.WeatherIOTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (checkInternetAvailability(context = this)) {
                if (!viewModel.getLastLocation().isNullOrEmpty()
                ) {
                    viewModel.loadWeatherInfoByCity(city = viewModel.getLastLocation()!!)
                } else {
                    viewModel.loadWeatherInfo()
                }
            } else {
                viewModel.currentState = viewModel.currentState.copy(
                    isLoading = false,
                    error = "No internet connection available. Please check your internet connection ."
                )
                viewModel.state = viewModel.state.copy(
                    isLoading = false,
                    error = "No internet connection available. Please check your internet connection ."
                )
            }
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            WeatherIOTheme {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Purple40)
                    ) {
                        WeatherSearchBar(onCityClick = ::getWeatherDataByCityRequest)
                        WeatherCard(
                            state = viewModel.currentState,
                            backgroundColor = Color.White.copy(alpha = 0.5f)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        WeatherForecast(state = viewModel.state)
                    }
                    if (viewModel.state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    viewModel.state.error?.let { error ->
                        Text(
                            text = error,
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }

    private fun getWeatherDataByCityRequest(cityName: String) {
        viewModel.loadWeatherInfoByCity(city = cityName)
        viewModel.saveLastLocation(location = cityName)
    }
}