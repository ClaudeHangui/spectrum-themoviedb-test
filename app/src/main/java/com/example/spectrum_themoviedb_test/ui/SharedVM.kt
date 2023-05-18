package com.example.spectrum_themoviedb_test.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spectrum_themoviedb_test.data.MoviesRepository
import com.example.spectrum_themoviedb_test.util.InternetStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedVM @Inject constructor(private val internetStateManager: InternetStateManager, private val moviesRepository: MoviesRepository):
    ViewModel() {

    private val _internetUiState = MutableStateFlow(false)
    val internetUiState = _internetUiState.asStateFlow()

    fun monitorInternetStateChange() = viewModelScope.launch {
        internetStateManager.observeNetworkChangeState().collectLatest {
            _internetUiState.value = it
        }
    }

    init {
        getMovieGenres()
    }

    private fun getMovieGenres() = viewModelScope.launch(Dispatchers.IO) {
       moviesRepository.fetchMovieGenres()
    }
}