package com.example.spectrum_themoviedb_test.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.example.spectrum_themoviedb_test.data.Genre
import com.example.spectrum_themoviedb_test.data.MovieItem

@Dao
interface MoviesDao {
    @Upsert
    fun insertAllGenres(genres: List<Genre>)

    @Upsert
    fun bookmarkMovie(movie: MovieItem)

    @Delete
    fun unBookmarkMovie(movie: MovieItem)

    @Query("select * from Movie")
    fun getAllBookmarkedMovies(): List<MovieItem>

}