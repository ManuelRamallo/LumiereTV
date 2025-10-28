package com.mramallo.lumieretv.data.api

sealed class Response<T>(val data: T? = null, val error: String? = null) {
    class Loading<T> : Response<T>()
    class Success<T>(data: T? = null) : Response<T>(data)
    class Error<T>(error: String? = null) : Response<T>(error = error)

}