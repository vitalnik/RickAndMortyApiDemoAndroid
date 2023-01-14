package com.example.rickandmorty.ui.screens.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.rickandmorty.CHARACTERS_SCREEN_ROUTE
import com.example.rickandmorty.EPISODES_SCREEN_ROUTE
import com.example.rickandmorty.HOME_SCREEN_ROUTE
import com.example.rickandmorty.LOCATIONS_SCREEN_ROUTE
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
