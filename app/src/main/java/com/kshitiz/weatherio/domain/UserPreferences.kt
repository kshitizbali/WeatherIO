package com.kshitiz.weatherio.domain

import android.content.Context
import androidx.preference.PreferenceManager

interface UserPreferences {
    //fun saveStringToPreferences()

    fun saveLastLocation(location: String)

    //fun getStringFromPreferences(): String?

    fun getLastLocation(): String?
}