package com.example.spectrum_themoviedb_test.ui.homepage.state

import com.example.spectrum_themoviedb_test.data.model.BaseMovieModel
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<BaseMovieModel> = emptyList(),
    val throwable: String? = null,
    val nextPageToView: Int = 1,
    val isScreenInit: Boolean = true
)

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: UiMovieDetail,
    val throwable: String? = null) {
    companion object {
        val EMPTY = MovieDetailState(false, UiMovieDetail.EMPTY, null)
    }
}