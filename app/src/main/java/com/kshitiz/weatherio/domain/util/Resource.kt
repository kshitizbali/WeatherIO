package com.kshitiz.weatherio.domain.util

/**
 * A generic class that holds a value with its loading status and a failure status
 * Along with this class, Normally i would also make use of  error codes from api and network when catching
 * an error for better understanding and readability of the error.
 * @param <T>
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(message: String, data: T? = null): Resource<T>(data, message)
}