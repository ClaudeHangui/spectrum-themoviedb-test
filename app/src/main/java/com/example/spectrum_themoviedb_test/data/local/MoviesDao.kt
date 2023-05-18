package com.example.spectrum_themoviedb_test.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.example.spectrum_themoviedb_test.data.Genre
import com.example.spectrum_themoviedb_test.data.model.MovieItem

@Dao
interface MoviesDao {
    @Insert(onConflict = androidx.room.OnConflictStrategy.REPLACE)
    fun insertAllGenres(genres: List<Genre>)

    @Upsert
    fun bookmarkMovie(movie: MovieItem)

    @Delete
    fun unBookmarkMovie(movie: MovieItem)

    @Query("select * from Movie")
    fun getAllBookmarkedMovies(): List<MovieItem>
    @Query("select name from MovieGenre where id = :id")
    fun getGenre(id: Int): String

}