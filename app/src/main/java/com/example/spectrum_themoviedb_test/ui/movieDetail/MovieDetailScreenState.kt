package com.example.spectrum_themoviedb_test.ui.movieDetail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.R
import com.example.spectrum_themoviedb_test.ui.commons.InternetConnectivityManger
import com.example.spectrum_themoviedb_test.ui.commons.ShowLoader
import com.example.spectrum_themoviedb_test.ui.utils.isInternetError

@Composable
fun BoxScope.MovieDetailScreenState(
    viewModel: MovieDetailVM = hiltViewModel()
) {
    val state by viewModel.movieDetailState.collectAsStateWithLifecycle()
    if (state.isLoading) {
        ShowLoader()
    }
    state.throwable?.let { error ->
        ConstraintLayout(
            modifier = Modifier
                .fillMaxSize()
        ) {
            val errorText = createRef()
            Text(
                text = if (error.isInternetError()){
                    stringResource(id = R.string.no_internet_connection)
                } else {
                    stringResource(id = R.string.something_went_wrong)
                },
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .constrainAs(errorText) {
                        linkTo(parent.top, parent.bottom, bias = 0.6f)
                    }
            )

        }


        InternetConnectivityManger {
            //viewModel.refreshTopRatedScreen()
        }
    }
}