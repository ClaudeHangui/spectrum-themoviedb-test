package com.example.spectrum_themoviedb_test.ui.bookmarkedMovies

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.ui.homepage.components.MovieCard

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoritesMoviesScreen(
    navigateBack: () -> Unit,
    navigateMovieDetail: (Int) -> Unit,
    viewModel: FavoritesMoviesVM = hiltViewModel()
) {

    val lazyListState = rememberLazyStaggeredGridState()
    val state by viewModel.favoritesMoviesState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.LightGray)
    ) {
        Column {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.favorites_movies),
                        modifier = Modifier.padding(top = 16.dp),
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        color = Color.White,

                        //color = Color.DarkGray,
                        //textAlign = TextAlign.Center
                    )
                },
                backgroundColor = Color(0xFF6650a4),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(align = Alignment.CenterVertically),
            )

            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalItemSpacing = 10.dp,
                state = lazyListState,
            ) {
                val movies = state.movies
                items(movies) { movieItem ->
                    MovieCard(
                        movieItem = movieItem,
                        onClick = {
                            navigateMovieDetail(movieItem.movieId)
                        })
                }
            }
        }
    }

}