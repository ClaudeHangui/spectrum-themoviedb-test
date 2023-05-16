package com.example.spectrum_themoviedb_test.data.model

data class UiMovieDetail(
    val movieId: Int,
    val posterPath: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val genre: List<String>,
    val overview: String,
    val backDropPath: String,
    val status: String
)