package com.example.rickandmorty.app.ui.screens.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.rickandmorty.app.Screen
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = Screen.Home.route) {

        val viewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            onNavigateToCharacters = {
                viewModel.logCharactersScreenView()
                navController.navigate(route = Screen.Characters.route)
            },
            onNavigateToLocations = {
                viewModel.logLocationsScreenView()
                navController.navigate(route = Screen.Locations.route)
            },
            onNavigateToEpisodes = {
                viewModel.logEpisodesScreenView()
                navController.navigate(route = Screen.Episodes.route)
            }
        )
    }
}
