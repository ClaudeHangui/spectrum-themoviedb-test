package com.example.spectrum_themoviedb_test.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.spectrum_themoviedb_test.data.Genre
import com.example.spectrum_themoviedb_test.data.model.BaseMovieModel
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.data.model.UiMovieItem

@Dao
interface MoviesDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertAllGenres(genres: List<Genre>)

    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun bookmarkMovie(movie: UiMovieDetail): Long

    @Delete
    fun unBookmarkMovie(movie: UiMovieDetail): Int

    @Query("select * from Movie")
    fun getAllBookmarkedMovies(): List<UiMovieDetail>
    @Query("select name from MovieGenre where id = :id")
    fun getGenre(id: Int): String

    @Query("select * from Movie where id = :id")
    suspend fun getMovie(id: Int): UiMovieDetail?
}