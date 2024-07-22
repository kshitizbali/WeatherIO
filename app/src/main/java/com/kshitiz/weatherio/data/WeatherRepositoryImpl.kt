package com.kshitiz.weatherio.data

import android.util.Log
import com.kshitiz.weatherio.data.mapper.toCurrentWeatherInfo
import com.kshitiz.weatherio.data.mapper.toWeatherInfo
import com.kshitiz.weatherio.data.remote.WeatherIOApi
import com.kshitiz.weatherio.domain.model.CurrentWeatherInfo
import com.kshitiz.weatherio.domain.model.WeatherInfo
import com.kshitiz.weatherio.domain.repository.WeatherRepository
import com.kshitiz.weatherio.domain.util.Resource
import javax.inject.Inject

/**
 * Implementation class for weather repository.
 * Injected constructor params
 * @param api WeatherIOApi to use the weather apis.
 */
class WeatherRepositoryImpl @Inject constructor(
    private val api: WeatherIOApi
): WeatherRepository {

    /**
     * Weather forecast of a location.
     * @param lat A string representing the latitude of a location.
     * @param long A string representing the longitude of a location.
     */
    override suspend fun getWeatherData(lat: Double, long: Double): Resource<WeatherInfo> {
        return try {
            Log.d(
                "Bali getWeatherData", "" + api.getWeatherData(
                    lat = lat,
                    lon = long
                )
            )
            Resource.Success(
                data = api.getWeatherData(
                    lat = lat,
                    lon = long
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    /**
     * Weather forecast by city
     * @param city: A String representing a city.
     */
    override suspend fun getWeatherDataByCity(city: String): Resource<WeatherInfo> {
        return try {
            Log.d(
                "Bali city", "" + api.getWeatherByCity(
                    city.plus(",us")
                )
            )
            Log.d(
                "Bali city", "" + api.getWeatherByCity(
                    city.plus(",us")
                ).toWeatherInfo()
            )

            Resource.Success(
                data = api.getWeatherByCity(
                    city.plus(",us")
                ).toWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    /**
     * Current weather data for a location.
     * @param lat A string representing the latitude of a location.
     * @param lon A string representing the longitude of a location.
     */
    override suspend fun getCurrentWeather(
        lat: Double,
        long: Double
    ): Resource<CurrentWeatherInfo> {
        return try {
            Log.d(
                "Bali getCurrentWeat", "" + api.getCurrentWeatherData(
                    lat = lat,
                    lon = long
                )
            )
            Resource.Success(
                data = api.getCurrentWeatherData(
                    lat = lat,
                    lon = long
                ).toCurrentWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    /**
     * Current weather for a city.
     * @param city: A string which represents a chosen city.
     */
    override suspend fun getCurrentWeatherByCity(city: String): Resource<CurrentWeatherInfo> {
        return try {
            Resource.Success(
                data = api.getCurrentWeatherByCity(
                    city.plus(",us")
                ).toCurrentWeatherInfo()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}