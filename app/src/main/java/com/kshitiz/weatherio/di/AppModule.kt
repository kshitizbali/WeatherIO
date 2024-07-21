package com.kshitiz.weatherio.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kshitiz.weatherio.BuildConfig
import com.kshitiz.weatherio.data.remote.WeatherIOApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

/**
 * DI module for app.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Create a logging interceptor
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Build OkHttpClient with logging interceptor
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    /*val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()*/
    /**
     * Initialize and injects weather api using retrofit.
     */
    @Provides
    @Singleton
    fun provideWeatherApi(): WeatherIOApi {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    /**
     * Initializes and injects location provider.
     */
    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(app)
    }

    /**
     * Initializes and injects the EncryptedSharePreferences.
     */
    @Singleton
    @Provides
    fun provideEncryptedSharedPreferences(@ApplicationContext appContext: Context): SharedPreferences {
        return EncryptedSharedPreferences.create(
            BuildConfig.WEATHER_IO_PREF,
            createMasterKey(),
            appContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    /**
     * Helper method to create master keys for EncryptedSharedPreferences.
     */
    private fun createMasterKey(): String {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        return MasterKeys.getOrCreate(keyGenParameterSpec)
    }
}