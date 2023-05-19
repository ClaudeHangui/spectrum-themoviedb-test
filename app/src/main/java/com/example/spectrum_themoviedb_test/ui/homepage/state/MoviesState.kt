package com.example.spectrum_themoviedb_test.ui.homepage.state

import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<UiMovieItem> = emptyList(),
    val throwable: String? = null,
    val nextPageToView: Int = 1
)

data class MovieDetailState(
    val isLoading: Boolean = false,
    val movieDetail: UiMovieDetail? = null,
    val throwable: String? = null)