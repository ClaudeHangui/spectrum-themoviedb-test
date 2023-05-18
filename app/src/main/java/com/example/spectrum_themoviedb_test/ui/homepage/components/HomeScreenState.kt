package com.example.spectrum_themoviedb_test.ui.homepage.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.spectrum_themoviedb_test.ui.MoviesVM

@Composable
fun BoxScope.HomeScreenState(viewModel: MoviesVM = hiltViewModel()) {
    val state by viewModel.nowPlayingState.collectAsStateWithLifecycle()
    state.let {
        if(it.isLoading){
            ShowLoader()
        }
        it.throwable?.let { error ->
            if (error.isNotEmpty()){
                Text(
                    text = error,
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

}

@Composable
fun ShowLoader() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 64.dp),
            color = Color.Magenta,
            strokeWidth = 6.dp
        )
    }

}