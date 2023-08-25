package com.example.rickandmorty.app.ui.screens.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.rickandmorty.app.Screen

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
