package com.example.spectrum_themoviedb_test.data

sealed class ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>()
    data class Failure<T>(val data: T?): ResultState<T>()
}
