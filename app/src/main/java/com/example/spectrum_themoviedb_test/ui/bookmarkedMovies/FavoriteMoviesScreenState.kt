package com.example.spectrum_themoviedb_test.ui.bookmarkedMovies

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.ui.commons.ShowLoader


@Composable
fun BoxScope.FavoriteMoviesScreenState(
    viewModel: FavoritesMoviesVM = hiltViewModel()
) {
    val state by viewModel.favoritesMoviesState.collectAsStateWithLifecycle()
    state.let {
        if (it.isLoading) {
            ShowLoader()
        }

        if(it.movies.isEmpty()){
            ShowNoBookmarks()
        }

        it.throwable?.let { error ->
            error.printStackTrace()
            Text(
                text = stringResource(id = R.string.something_went_wrong),
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
fun ShowNoBookmarks() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.FavoriteBorder,
            tint = Color.DarkGray,
            modifier = Modifier.size(size = 64.dp),
            contentDescription = null)

        Text(
            text = stringResource(id = R.string.no_bookmarks),
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)

        )
    }
}
