package com.example.spectrum_themoviedb_test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class MoviesApiResponse(
    val dates: Dates,
    override val page: Int,
    override val results: List<MovieItem>,
    override val total_results: Int,
    override val total_pages: Int,
    ): BaseApiResponse {
    data class Dates(
        val maximum: String,
        val minimum: String
    )
}
data class PopularMoviesApiResponse(
    override val page: Int,
    override val results: List<MovieItem>,
    override val total_results: Int,
    override val total_pages: Int,
    ): BaseApiResponse

interface BaseApiResponse {
    val page: Int
    val results: List<MovieItem>
    val total_results: Int
    val total_pages: Int
}

@Entity(tableName = "Movie")
data class MovieItem(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    @PrimaryKey val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)