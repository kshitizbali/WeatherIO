package com.kshitiz.weatherio.data.mapper

import com.kshitiz.weatherio.data.dto.WeatherCurrentDto
import com.kshitiz.weatherio.data.dto.WeatherDataDto
import com.kshitiz.weatherio.data.dto.WeatherMainDto
import com.kshitiz.weatherio.domain.model.CurrentWeatherData
import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo
import com.kshitiz.weatherio.domain.model.WeatherData
import com.kshitiz.weatherio.domain.model.WeatherInfo
import com.kshitiz.weatherio.domain.model.WeatherType
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

/**
 * Converts the [WeatherCurrentDto] to [CurrentWeatherInfo]
 */
fun WeatherCurrentDto.toCurrentWeatherInfo(): CurrentWeatherInfo {
    val weatherData = weatherData.first()
    val weatherType = WeatherType.fromWMO(weatherData.id, weatherData.icon)
    return CurrentWeatherInfo(
        CurrentWeatherData(
            time = Instant.ofEpochSecond(dateTime)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime(),
            temperatureFahren = main.temperature,
            pressure = main.pressure,
            windSpeed = wind.speed,
            humidity = main.humidity,
            weatherType = weatherType,
            name = name
        )
    )
}

/**
 * Converts the [WeatherMainDto] to [WeatherInfo] by filtering the forecast for tomorrow
 */
fun WeatherMainDto.toWeatherInfo(): WeatherInfo {
    val today = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    val filteredForecastByToday = weatherData.filter { it.dateTime.startsWith(today) }
        .map {
            convertToWeatherData(0, it)
        }
    val weatherDataMap = mapOf(0 to filteredForecastByToday)

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap
    )
}

/**
 * Converts the [WeatherDataDto] to [WeatherData]
 */
private fun convertToWeatherData(index: Int, data: WeatherDataDto): WeatherData {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val temperature = data.main.temperature
    val weatherCode = data.weather[0].id
    val weatherIcon = data.weather[0].icon
    val windSpeed = data.wind.speed
    val pressure = data.main.pressure
    val humidity = data.main.humidity
    val dateTime = data.dateTime
    return WeatherData(
        time = LocalDateTime.parse(
            dateTime,
            dateFormatter
        ),
        temperatureFahren = temperature,
        pressure = pressure,
        windSpeed = windSpeed,
        humidity = humidity,
        weatherType = WeatherType.fromWMO(weatherCode, weatherIcon)
    )
}