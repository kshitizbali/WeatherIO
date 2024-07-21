package com.kshitiz.weatherio.data

import android.content.Context
import android.content.SharedPreferences
import com.kshitiz.weatherio.domain.UserPreferences
import javax.inject.Inject

/**
 * User preferences implementation class.
 * Injected constructor params
 * @param preferences Preferences to use the SharedPreferences built in method to save
 * and fetch data.
 */
class UserPreferencesImpl @Inject constructor(
    private val preferences: SharedPreferences
) : UserPreferences {
    val key = "last_location"
    /**
     * Save the last location of the user session in encrypted preferences.
     */
    override fun saveLastLocation(location: String) {

        with(preferences.edit()) {
            putString(key, location)
            commit()
        }
    }
    /**
     * Fetches the last location saved in the preferences.
     */
    override fun getLastLocation(): String? {
        return preferences.getString(key, "")
    }
}