package com.kshitiz.weatherio.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.kshitiz.weatherio.domain.model.WeatherData
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

/**
 * Composable UI component for displaying Hourly Weather of a current day.
 * @param weatherData The weekly weather data to show
 * @param modifier Modifier to customize the view component.
 * @param textColor Specifies the text color.
 */
@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {
    val formattedTime = remember(weatherData) {
        weatherData.time.format(
            DateTimeFormatter.ofPattern("HH:mm")
        )
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = formattedTime,
            color = Color.LightGray
        )
        Image(
            painter = rememberImagePainter(data = weatherData.weatherType.iconUrlDouble),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperatureFahren.roundToInt()}°F",
            color = textColor,
            fontWeight = FontWeight.Bold
        )
    }
}