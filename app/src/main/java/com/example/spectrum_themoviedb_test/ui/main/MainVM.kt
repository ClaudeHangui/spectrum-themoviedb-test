package com.example.spectrum_themoviedb_test.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(private val moviesRepository: MoviesRepository): ViewModel() {

    fun getMovieGenres() = viewModelScope.launch(Dispatchers.IO) {
        moviesRepository.fetchMovieGenres()
    }
}