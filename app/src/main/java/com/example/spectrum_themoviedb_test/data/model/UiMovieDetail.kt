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
    @PrimaryKey override var movieId: Int = -1,
    override var title: String?,
    @ColumnInfo(name = "backdrop_path")
    var backDropPath: String?,
    @ColumnInfo(name = "poster_path")
    override var posterPath: String?,
    @ColumnInfo(name = "vote_average")
    override var voteAverage: Double?,
    @ColumnInfo(name = "vote_count")
    override var voteCount: Int?,
    @ColumnInfo(name = "release_date")
    override var releaseDate: String?,
    @ColumnInfo(name = "movie_genres")
    override var genre: List<String>?,
    var overview: String?,
    var status: String?,
    @ColumnInfo(name = "tag_line")
    var tagLine: String?,
    @ColumnInfo(name = "spoken_languages")
    var spokenLanguages: List<String>?,
    @Ignore
    var bookmarked: Boolean = false
): BaseMovieModel{
    companion object {
        val EMPTY = UiMovieDetail()
    }
    constructor(): this(-1, null, null, null, null, null, null, null, null, null, null, null, false)
}