package com.kshitiz.weatherio.data

import android.content.SharedPreferences
import javax.inject.Inject

class UserPreferencesImpl @Inject constructor(
    private  val preferences: SharedPreferences
) {
}