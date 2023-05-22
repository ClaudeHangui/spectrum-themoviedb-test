package com.example.spectrum_themoviedb_test.data.model

interface BaseMovieModel {
    val movieId: Int
    val posterPath: String?
    val title: String?
    val voteAverage: Double?
    val voteCount: Int?
    val releaseDate: String?
    val genre: List<String>?
}