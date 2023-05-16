package com.example.spectrum_themoviedb_test.data.remote

/**
 * list of errors we might have when making an http call
 * this list is non-exhaustive, so we decided to explicitly handle a few of them; should the server return
 * an error not known and/or handled on the client side, the UnknownError is returned
 */
sealed class FailureType {
    object NetworkError : FailureType()
    object ServerError : FailureType()
    object BadRequestError : FailureType()
    object GatewayError : FailureType()
    object UnknownError : FailureType()
}
