package com.kshitiz.weatherio.domain.util

import android.util.Log
import com.kshitiz.weatherio.BuildConfig


object WeatherIconUtils {
    /**
     * Builds the full URL for the weather icon.
     *
     * @param iconCode The icon code returned by the OpenWeatherMap API.
     * @param size The size of the icon (default is "2x").
     * @return The full URL for the weather icon.
     */
    fun buildIconUrl(iconCode: String, size: String = ""): String {
        Log.d("Bali ICON URL", "${BuildConfig.ICON_URL}${iconCode}$size.png")
        return "${BuildConfig.ICON_URL}${iconCode}$size.png"
    }
}
