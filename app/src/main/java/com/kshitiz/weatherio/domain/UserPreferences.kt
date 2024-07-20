package com.kshitiz.weatherio.domain

import android.content.Context
import androidx.preference.PreferenceManager

interface UserPreferences {
    //fun saveStringToPreferences()

    fun saveLastLocation()

    //fun getStringFromPreferences(): String?

    fun getLastLocation(context: Context): String?
}