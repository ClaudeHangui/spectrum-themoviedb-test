package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.data.remote.FailureType

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failure<T>(val failure: FailureType, val data: T?) : ResultState<T>()
}
