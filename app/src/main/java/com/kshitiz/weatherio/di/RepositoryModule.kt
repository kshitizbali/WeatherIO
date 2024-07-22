package com.kshitiz.weatherio.di

import com.kshitiz.weatherio.data.UserPreferencesImpl
import com.kshitiz.weatherio.data.WeatherRepositoryImpl
import com.kshitiz.weatherio.domain.UserPreferences
import com.kshitiz.weatherio.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Singleton

/**
 * Module that provides all dependency injection for the repo module.
 */
@ExperimentalCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Binds WeatherRepository.
     */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    /**
     * Binds user preferences repo.
     */
    @Binds
    @Singleton
    abstract fun provideUserPreferencesRepository(
        userPreferencesImpl: UserPreferencesImpl
    ): UserPreferences
}