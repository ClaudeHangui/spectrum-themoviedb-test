package com.example.spectrum_themoviedb_test.data

data class GenreApiResponse(
    val genres: List<Genre>
) {
    data class Genre(
        val id: Int,
        val name: String
    )
}