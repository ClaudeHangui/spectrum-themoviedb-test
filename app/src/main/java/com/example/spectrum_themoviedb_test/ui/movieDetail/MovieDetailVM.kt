package com.example.spectrum_themoviedb_test.ui.movieDetail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.data.model.UiMovieDetail
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
class MovieDetailVM @Inject constructor(
    private val moviesRepository: MoviesRepository) : ViewModel() {


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
                it.copy(throwable = error, isLoading = false)
            }
        }.collect { response ->
            _movieDetailState.update { oldData ->
                oldData.copy(
                    movieDetail = response,
                    throwable = null,
                    isLoading = false
                )
            }
        }
    }

    fun setBookmarkState(movieId: UiMovieDetail, bookmarkButtonState: Boolean) =
        viewModelScope.launch(Dispatchers.Main) {
            val rowId = withContext(Dispatchers.IO) {
                if (bookmarkButtonState) {
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