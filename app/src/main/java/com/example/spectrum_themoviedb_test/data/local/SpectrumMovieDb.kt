package com.example.spectrum_themoviedb_test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spectrum_themoviedb_test.data.Genre
import com.example.spectrum_themoviedb_test.data.MovieItem

@Database(entities = [Genre::class, MovieItem::class], version = 1)
@TypeConverters(IntListTypeConverter::class)
abstract class SpectrumMovieDb: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}