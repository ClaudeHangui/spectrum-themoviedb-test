package com.example.spectrum_themoviedb_test.ui.homepage.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.BottomBarNav
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.HomeGraph
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberAnimatedNavController()) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        bottomBar = { BottomBarNav(navController = navController) },
        scaffoldState = scaffoldState
    ) { paddingValues ->
        HomeGraph(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController
        )
    }
}

