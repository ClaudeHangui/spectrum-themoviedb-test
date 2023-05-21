package com.example.spectrum_themoviedb_test.ui.movieDetail

import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: UiMovieDetail,
    val throwable: String? = null) {
    companion object {
        val EMPTY = MovieDetailState(false, UiMovieDetail.EMPTY, null)
    }
}