package com.example.spectrum_themoviedb_test.ui.homepage.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBarNav(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return

    BottomNavigation {
        val homeSelected = currentRoute == NavRoute.Home.path
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    tint = androidx.compose.ui.graphics.Color.LightGray,
                    contentDescription = "Home" )
            },
            selected = homeSelected,
            onClick = {
                if (!homeSelected) {
                    navController.navigate(NavRoute.Home.path) {
                        popUpTo(NavRoute.Home.path) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            label = {
                 Text(text = "Now Playing")
            })

        val popularSelected = currentRoute == NavRoute.Popular.path
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    tint = androidx.compose.ui.graphics.Color.LightGray,
                    contentDescription = "Popular" )
            },
            selected = popularSelected,
            onClick = {
                if (!popularSelected) {
                    navController.navigate(NavRoute.Popular.path){
                        popUpTo(NavRoute.Popular.path) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            label = {
                 Text(text = "Popular")
            })

        val topSelected = currentRoute == NavRoute.Top.path
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    tint = androidx.compose.ui.graphics.Color.LightGray,
                    contentDescription = "Top Rated")
            },
            selected = topSelected,
            onClick = {
                if (!topSelected) {
                    navController.navigate(NavRoute.Top.path){
                        popUpTo(NavRoute.Top.path) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            label = {
                 Text(text = "Top Rated")
            })

        val upcomingSelected = currentRoute == NavRoute.Upcoming.path
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    tint = androidx.compose.ui.graphics.Color.LightGray,
                    contentDescription = "Upcoming" )
            },
            selected = upcomingSelected,
            onClick = {
                if (!upcomingSelected) {
                    navController.navigate(NavRoute.Upcoming.path){
                        popUpTo(NavRoute.Upcoming.path) {
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            label = {
                 Text(text = "Upcoming")
            })
    }
}