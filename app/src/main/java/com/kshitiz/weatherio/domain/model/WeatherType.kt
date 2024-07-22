package com.kshitiz.weatherio.domain.model

import androidx.annotation.DrawableRes
import com.kshitiz.weatherio.R
import com.kshitiz.weatherio.domain.util.WeatherIconUtils

/**
 * Sealed class to organize weather type along with its accompanying
 * icon URL and description.
 */
sealed class WeatherType(
    val weatherDesc: String,
    val iconCode: String
) {
    val iconUrl: String
        get() = WeatherIconUtils.buildIconUrl(iconCode)

    val iconUrlDouble: String
        get() = WeatherIconUtils.buildIconUrl(iconCode, "@2x")

    val iconUrlQuadruple: String
        get() = WeatherIconUtils.buildIconUrl(iconCode, "@4x")

    data object ClearSkyDay : WeatherType("Clear sky", "01d")
    data object ClearSkyNight : WeatherType("Clear sky", "01n")
    data object FewCloudsDay : WeatherType("Few clouds", "02d")
    data object FewCloudsNight : WeatherType("Few clouds", "02n")
    data object ScatteredClouds : WeatherType("Scattered clouds", "03d")
    data object BrokenClouds : WeatherType("Broken clouds", "04d")
    data object ShowerRain : WeatherType("Shower rain", "09d")
    data object Rain : WeatherType("Rain", "10d")
    data object Thunderstorm : WeatherType("Thunderstorm", "11d")
    data object Snow : WeatherType("Snow", "13d")
    data object Mist : WeatherType("Mist", "50d")

    companion object {
        fun fromWMO(code: Int, iconCode: String): WeatherType {
            return when (code) {
                200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> Thunderstorm
                300, 301, 302, 310, 311, 312, 313, 314, 321 -> ShowerRain
                500, 501, 502, 503, 504, 511, 520, 521, 522, 531 -> Rain
                600, 601, 602, 611, 612, 613, 615, 616, 620, 621, 622 -> Snow
                701, 711, 721, 731, 741, 751, 761, 762, 771, 781 -> Mist
                800 -> if (iconCode.endsWith("n")) ClearSkyNight else ClearSkyDay
                801 -> if (iconCode.endsWith("n")) FewCloudsNight else FewCloudsDay
                802 -> ScatteredClouds
                803, 804 -> BrokenClouds
                else -> ClearSkyDay
            }
        }
    }
}
