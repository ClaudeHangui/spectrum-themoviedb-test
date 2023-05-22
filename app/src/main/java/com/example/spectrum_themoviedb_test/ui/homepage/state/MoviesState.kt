package com.example.spectrum_themoviedb_test.ui.homepage.state

import com.example.spectrum_themoviedb_test.data.model.BaseUiMovieModel

data class MoviesState(
    val isLoading: Boolean = false,
    val movies: List<BaseUiMovieModel> = emptyList(),
    val throwable: Throwable? = null,
    val nextPageToView: Int = 1,
    val isScreenInit: Boolean = true, // search
    val isNoMovieFound: Boolean = false,  // search
)

