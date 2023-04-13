package com.example.cinema_ticketing_app.core.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.cinema_ticketing_app.module.detail.presentation.DetailScreen
import com.example.cinema_ticketing_app.module.home.model.nowPlayingMovie
import com.example.cinema_ticketing_app.module.home.presentation.HomeScreen

object AppRoute {
    @Composable
    fun GenerateRoute(navController: NavHostController) {
        NavHost(
            navController = navController,
            startDestination = AppRouteName.Home,
        ) {
            composable(AppRouteName.Home) {
                HomeScreen(navController = navController)
            }
            composable("${AppRouteName.Detail}/{id}") { backStackEntry ->
                val id = backStackEntry.arguments?.getString("id")
                val movie = nowPlayingMovie.first{ it.id == id }

                DetailScreen(navController = navController, movie)
            }
        }
    }
}