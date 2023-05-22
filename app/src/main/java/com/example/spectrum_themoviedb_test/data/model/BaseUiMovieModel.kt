package com.example.spectrum_themoviedb_test.data.model

interface BaseUiMovieModel {
    val movieId: Int
    val posterPath: String?
    val title: String?
    val voteAverage: Double?
    val voteCount: Int?
    val releaseDate: String?
    val genre: List<String>?
}