package com.example.mywishlistapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun Navigation(
    viewModel: WishViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val countObservable = remember { CountObservable() }

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(route = Screen.HomeScreen.route) {
            HomeView(navController, viewModel, countObservable)
        }

        composable(route = Screen.CountScreen.route) {
            CountScreen(countObservable)
        }

        composable(
            route = Screen.AddScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) {
            val id = it.arguments?.getLong("id") ?: 0L
            AddEditDetailView(id = id, viewModel = viewModel, navController = navController)
        }
    }
}