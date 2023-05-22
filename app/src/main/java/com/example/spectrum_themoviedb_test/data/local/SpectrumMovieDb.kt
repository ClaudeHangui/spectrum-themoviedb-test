package com.example.spectrum_themoviedb_test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spectrum_themoviedb_test.data.model.Genre
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail

@Database(entities = [Genre::class, UiMovieDetail::class], version = 1)
@TypeConverters(IntListTypeConverter::class, StringListTypeConverter::class)
abstract class SpectrumMovieDb: RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}