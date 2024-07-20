package com.kshitiz.weatherio.data

import android.content.Context
import android.content.SharedPreferences
import com.kshitiz.weatherio.domain.UserPreferences
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private val preferences: SharedPreferences
) : UserPreferences {
    val key = "last_location"
    override fun saveLastLocation(location: String) {

        with(preferences.edit()) {
            putString(key, location)
            commit()
        }
    }

    override fun getLastLocation(): String? {
        return preferences.getString(key, "Palo Alto")
    }
}