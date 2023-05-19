package com.example.spectrum_themoviedb_test.ui.movieDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.ui.homepage.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailVM @Inject constructor( private val moviesRepository: MoviesRepository,
                                         private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val _movieDetailState = MutableStateFlow(MovieDetailState())
    val movieDetailState = _movieDetailState.asStateFlow()


    fun getMovie(movieId: Int) = viewModelScope.launch {
        moviesRepository.fetchMovieDetail(movieId).onStart {
            _movieDetailState.update { it.copy(isLoading = true) }
        }.catch { error ->
            Log.e("MovieDetailVM", "error: ${error.message}")
            _movieDetailState.update {
                it.copy(throwable = error.message ?: "Something went wrong", isLoading = false)
            }
        }.collect { response ->
            Log.e("MovieDetailVM", "updating the local mutable flow")
            _movieDetailState.update { oldData ->
                oldData.copy(
                movieDetail = response,
                isLoading = false) }
        }
    }
}