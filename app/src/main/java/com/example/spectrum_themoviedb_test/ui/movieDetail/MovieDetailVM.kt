package com.example.spectrum_themoviedb_test.ui.movieDetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
import com.example.spectrum_themoviedb_test.ui.homepage.state.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MovieDetailVM @Inject constructor( private val moviesRepository: MoviesRepository,
                                         private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    val _movieDetailState = MutableStateFlow(MovieDetailState.EMPTY)
    val movieDetailState = _movieDetailState.asStateFlow()

    val _bookmarkState = MutableStateFlow(BookmarkMovieState())
    val bookmarkState = _bookmarkState.asStateFlow()

    fun getMovie(movieId: Int) = viewModelScope.launch {
        moviesRepository.fetchMovieDetail(movieId).onStart {
            _movieDetailState.update { it.copy(isLoading = true) }
        }.catch { error ->
            Log.e("MovieDetailVM", "error: ${error.message}")
            _movieDetailState.update {
                it.copy(throwable = error.message ?: "Something went wrong", isLoading = false)
            }
        }.collect { response ->
            _movieDetailState.update { oldData ->
                oldData.copy(
                movieDetail = response,
                isLoading = false) }
            _bookmarkState.update { it.copy(isBookmarked = response.bookmarked) }
        }
    }

    fun setBookmarkState(movieId: UiMovieDetail, bookmarkButtonState: Boolean) = viewModelScope.launch(Dispatchers.Main){
        val rowId = withContext(Dispatchers.IO){
            if(bookmarkButtonState){
                moviesRepository.addMovieToBookmarks(movieId)
            } else {
                moviesRepository.removeMovieFromBookmarks(movieId)
            }
        }
        if (rowId > 0) {
           _movieDetailState.update { it.copy(movieDetail = movieId.copy(bookmarked = rowId > 1)) }
        }
    }
}

data class BookmarkMovieState(
    val isBookmarked: Boolean = false
)