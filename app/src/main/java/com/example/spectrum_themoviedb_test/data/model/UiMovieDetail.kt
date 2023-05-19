package com.example.spectrum_themoviedb_test.data.model

data class UiMovieDetail(
    val movieId: Int,
    val title: String,
    val backDropPath: String,
    val posterPath: String,
    val voteAverage: Double,
    val voteCount: Int,
    val releaseDate: String,
    val genre: List<String>,
    val overview: String,
    val status: String,
    val tagLine: String,
    val spokenLanguages: List<String>,
)