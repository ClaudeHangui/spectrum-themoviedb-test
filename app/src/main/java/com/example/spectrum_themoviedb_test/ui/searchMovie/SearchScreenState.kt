package com.example.spectrum_themoviedb_test.ui.searchMovie

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.ui.commons.ShowLoader
import com.example.spectrum_themoviedb_test.ui.homepage.components.FailureState

@Composable
fun BoxScope.SearchScreenState(query: String,
    viewModel: SearchViewModel = hiltViewModel()) {
    val state by viewModel.searchedMoviesState.collectAsStateWithLifecycle()
    state.let {
        if (it.isLoading) {
            ShowLoader()
        }

        if (it.isScreenInit) {
            ShowInitScreen()
        }

        if (it.isNoMovieFound) {
            Text(
                text = stringResource(id = R.string.no_movie_found),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)

            )
        }

        FailureState(throwable = it.throwable, data = it.movies) {
            if (it.movies.isEmpty()){
                viewModel.refreshSearchData(query)
            } else {
                viewModel.loadMoreMovies()
            }
        }
    }
}

@Composable
fun ShowInitScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = R.drawable.movie_search_icon),
            modifier = Modifier.size(size = 200.dp),
            contentDescription = null)
    }
}

