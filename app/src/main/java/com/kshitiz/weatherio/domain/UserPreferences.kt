package com.kshitiz.weatherio.domain

import android.content.Context
import androidx.preference.PreferenceManager

/**
 * User Preferences repository interface.
 */
interface UserPreferences {
    //fun saveStringToPreferences()

    /**
     * Save the last location of the user session in encrypted preferences.
     */
    fun saveLastLocation(location: String)

    /**
     * Fetches the last location saved in the preferences.
     */
    fun getLastLocation(): String?
}