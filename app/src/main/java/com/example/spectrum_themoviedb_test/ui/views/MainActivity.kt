package com.example.spectrum_themoviedb_test.ui.views

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.spectrum_themoviedb_test.ui.coreNavigationGraph.RootNavigationGraph
import com.example.spectrum_themoviedb_test.ui.theme.SpectrumthemoviedbtestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModels: MainVM by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModels.getMovieGenres()
        setContent {
            SpectrumthemoviedbtestTheme {
                val navController = rememberNavController()
                Scaffold {
                    RootNavigationGraph(navHostController = navController)
                }
            }
        }
    }
}