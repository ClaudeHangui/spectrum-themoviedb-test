package com.example.spectrum_themoviedb_test.data.remote

import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import javax.inject.Inject

class HttpFailureFactory @Inject constructor() {
    fun produceFailure(exception: Exception): FailureType {
        return when (exception) {
            is IOException -> FailureType.NetworkError
            is HttpException -> {
                val response = exception.response()
                return handleHttpErrorCode(response)
            }
            else -> FailureType.UnknownError
        }
    }

    private fun <T> handleHttpErrorCode(response: Response<T>?): FailureType {
        return when (response?.code()) {
            HttpURLConnection.HTTP_INTERNAL_ERROR -> FailureType.ServerError
            HttpURLConnection.HTTP_BAD_GATEWAY -> FailureType.GatewayError
            HttpURLConnection.HTTP_BAD_REQUEST -> FailureType.BadRequestError
            else -> FailureType.UnknownError
        }
    }
}