package com.example.spectrum_themoviedb_test.ui.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.spectrum_themoviedb_test.ui.SharedVM
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.BottomBarNav
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.NavigationSetup
import com.example.spectrum_themoviedb_test.ui.theme.SpectrumthemoviedbtestTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModels : SharedVM by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModels.monitorInternetStateChange()
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SpectrumthemoviedbtestTheme {
        Greeting("Android")
    }
}


@Composable
private fun MainScreen() {
    SpectrumthemoviedbtestTheme {
        val navController = rememberNavController()
        Scaffold(
            bottomBar = { BottomBarNav(navController = navController) }
        ) { paddingValues ->
            NavigationSetup(
                modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
                navController = navController
            )
        }
    }
}