package com.example.spectrum_themoviedb_test.ui.homepage.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.BottomBarNav
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.HomeGraph
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.screens
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(navController: NavHostController = rememberAnimatedNavController()) {
    val scaffoldState = rememberScaffoldState()

    val bottomBarDestination = screens.any { it.route == currentRoute(navController) }

    Scaffold(
        bottomBar = { BottomBarNav(navController = navController) },
        scaffoldState = scaffoldState,
        topBar = {
            if (bottomBarDestination) {
                MyUI()
            }
        }
    ) { paddingValues ->
        HomeGraph(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController
        )
    }
}

@Composable
fun MyUI() {
    TopAppBar(
        title = {
            Text(text = "SemicolonSpace")
        }
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

