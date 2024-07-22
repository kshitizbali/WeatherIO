package com.kshitiz.weatherio.domain.location

import android.location.Location

/**
 * Interface to get the current location of the user.
 */
interface LocationTracker {
    /**
     * gets the current location.
     */
    suspend fun getCurrentLocation(): Location?
}