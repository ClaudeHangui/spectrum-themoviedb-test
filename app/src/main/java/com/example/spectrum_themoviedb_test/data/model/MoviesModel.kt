package com.example.spectrum_themoviedb_test.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MoviesApiResponse(
    val dates: Dates,
    override val page: Int,
    override val results: List<MovieListItem>,
    override val total_results: Int,
    override val total_pages: Int,
    ): BaseApiResponse

@Serializable
data class Dates(
    val maximum: String,
    val minimum: String
)
@Serializable
data class PopularMoviesApiResponse(
    override val page: Int,
    override val results: List<MovieListItem>,
    override val total_results: Int,
    override val total_pages: Int,
    ): BaseApiResponse

interface BaseApiResponse {
    val page: Int
    val results: List<MovieListItem>
    val total_results: Int
    val total_pages: Int
}

@Serializable
data class MovieListItem(
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String?,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)