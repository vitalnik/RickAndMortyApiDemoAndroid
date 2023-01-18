package com.example.rickandmorty.app.ui.screens.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.rickandmorty.app.CHARACTERS_SCREEN_ROUTE
import com.example.rickandmorty.app.EPISODES_SCREEN_ROUTE
import com.example.rickandmorty.app.HOME_SCREEN_ROUTE
import com.example.rickandmorty.app.LOCATIONS_SCREEN_ROUTE
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable(route = HOME_SCREEN_ROUTE) {

        val viewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            onNavigateToCharacters = {
                viewModel.logCharactersScreenView()
                navController.navigate(route = CHARACTERS_SCREEN_ROUTE)
            },
            onNavigateToLocations = {
                viewModel.logLocationsScreenView()
                navController.navigate(route = LOCATIONS_SCREEN_ROUTE)
            },
            onNavigateToEpisodes = {
                viewModel.logEpisodesScreenView()
                navController.navigate(route = EPISODES_SCREEN_ROUTE)
            }
        )
    }
}
