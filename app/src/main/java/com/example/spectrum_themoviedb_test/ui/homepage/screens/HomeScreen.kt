package com.example.spectrum_themoviedb_test.ui.homepage.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spectrum_themoviedb_test.ui.coreNavigationGraph.Destinations
import com.example.spectrum_themoviedb_test.ui.homepage.navigation.BottomBarNav
import com.example.spectrum_themoviedb_test.ui.coreNavigationGraph.HomeGraph
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
                MyUI(navController)
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
fun MyUI(navController: NavHostController) {

    TopAppBar(
        title = {
            Text(text = "The Movie DB")
        },
        //backgroundColor = Color.Transparent,
        elevation = 0.dp,
        actions = {
            TopAppBarActionButton(
                imageVector = Icons.Outlined.Search,
                description = "Search",
                onClick = {
                    navController.navigate(Destinations.SearchScreen.route)
                }
            )
            TopAppBarActionButton(
                imageVector = Icons.Outlined.Favorite,
                description = "Favorite",
                onClick = {
                    navController.navigate(Destinations.FavoriteMoviesScreen.route)

                }
            )
        }
    )
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun TopAppBarActionButton(
    imageVector: ImageVector,
    description: String,
    onClick: () -> Unit
) {
    IconButton(onClick = {
        onClick()
    }) {
        Icon(imageVector = imageVector, contentDescription = description)
    }
}


