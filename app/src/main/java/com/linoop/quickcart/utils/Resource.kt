package com.linoop.quickcart.utils

/**
 * Resource is a Generic sealed class to handle Network api state as well as Database api state
 */
sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?, message: String?) : Resource<T>(data, message)
    class Error<T>(data: T? = null, message: String?) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}