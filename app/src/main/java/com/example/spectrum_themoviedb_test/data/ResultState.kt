package com.example.spectrum_themoviedb_test.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.retryWhen
import java.io.IOException

private const val RETRY_TIME_IN_MILLIS = 15_000L
private const val RETRY_ATTEMPT_COUNT = 3
sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Failure(val exception: Throwable? = null) : ResultState<Nothing>
    object Loading : ResultState<Nothing>
}

fun <T> Flow<T>.asResult(): Flow<ResultState<T>> {
    return this
        .map<T, ResultState<T>> {
            ResultState.Success(it)
        }
        .onStart { emit(ResultState.Loading) }
        .retryWhen { cause, attempt ->
            if (cause is IOException && attempt < RETRY_ATTEMPT_COUNT) {
                delay(RETRY_TIME_IN_MILLIS)
                true
            } else {
                false
            }
        }
        .catch { emit(ResultState.Failure(it)) }
}