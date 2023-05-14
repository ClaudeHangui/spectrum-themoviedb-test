package com.example.spectrum_themoviedb_test.data

import androidx.room.Entity
import androidx.room.PrimaryKey

data class GenreApiResponse(
    val genres: List<Genre>
)

@Entity(tableName = "MovieGenre")
data class Genre(
    @PrimaryKey val id: Int,
    val name: String
)
