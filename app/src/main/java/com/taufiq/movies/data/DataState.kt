package com.taufiq.movies.data

sealed class DataState<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T?) : DataState<T>(data)
    class Loading<T> : DataState<T>()
    class Empty<T> : DataState<T>()
    class Error<T>(message: String?) : DataState<T>(message = message)
}
