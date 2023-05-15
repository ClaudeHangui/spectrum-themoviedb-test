package com.example.spectrum_themoviedb_test.data

import com.example.spectrum_themoviedb_test.data.remote.MovieDbApi
import com.skydoves.sandwich.map
import com.skydoves.sandwich.onError
import com.skydoves.sandwich.onException
import com.skydoves.sandwich.onSuccess
import javax.inject.Inject


class MoviesRepository @Inject constructor(private val api: MovieDbApi) {

    suspend fun fetchNowPlayingMovies(page: Int) {
        val apiResponse = api.getNowPlayingMovies(page)
        apiResponse.onSuccess {

        }.onError {

        }.onException {

        }
    }

}