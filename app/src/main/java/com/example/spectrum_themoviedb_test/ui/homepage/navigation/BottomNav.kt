package com.example.spectrum_themoviedb_test.ui.homepage.navigation

import android.util.Log
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.spectrum_themoviedb_test.ui.Destinations

@Composable
fun BottomBarNav(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val screens = listOf(
        Destinations.NowPlayingScreen,
        Destinations.PopularScreen,
        Destinations.TopRatedScreen,
        Destinations.UpcomingScreen
    )

    val currentDestination = navBackStackEntry?.destination
    val bottomBarDestination = screens.any { it.route == currentDestination?.route }
    if (bottomBarDestination) {
        Log.e("BottomBarNav", "bottomBarDestination: $bottomBarDestination")
        BottomNavigation {
            screens.forEach { screen ->
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: Destinations,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            screen.title?.let { Text(text = it) }
        },
        icon = {
            screen.icon?.let {
                Icon(
                    imageVector = it,
                    tint = Color.LightGray,
                    contentDescription = "Navigation Icon"
                )
            }
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    inclusive = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}