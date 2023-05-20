package com.example.spectrum_themoviedb_test.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "Movie")
@Serializable
data class UiMovieDetail(
    @ColumnInfo(name = "id")
    @PrimaryKey var movieId: Int = -1,
    var title: String = "",
    @ColumnInfo(name = "backdrop_path")
    var backDropPath: String? = null,
    @ColumnInfo(name = "poster_path")
    var posterPath: String? = null,
    @ColumnInfo(name = "vote_average")
    var voteAverage: Double = 0.0,
    @ColumnInfo(name = "vote_count")
    var voteCount: Int = 0,
    @ColumnInfo(name = "release_date")
    var releaseDate: String = "",
    @ColumnInfo(name = "movie_genres")
    var genre: List<String> = emptyList(),
    var overview: String = "",
    var status: String = "",
    @ColumnInfo(name = "tag_line")
    var tagLine: String = "",
    @ColumnInfo(name = "spoken_languages")
    var spokenLanguages: List<String> = emptyList(),
    @Ignore
    var bookmarked: Boolean = false
)