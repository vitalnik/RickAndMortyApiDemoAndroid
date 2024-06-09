package com.example.rickandmorty.app.ui.screens.home

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.rickandmorty.app.CharactersRoute
import com.example.rickandmorty.app.EpisodesRoute
import com.example.rickandmorty.app.HomeRoute
import com.example.rickandmorty.app.LocationsRoute

fun NavGraphBuilder.homeScreen(
    navController: NavHostController,
) {
    composable<HomeRoute> {

        val viewModel = hiltViewModel<HomeViewModel>()

        HomeScreen(
            onNavigateToCharacters = {
                viewModel.logCharactersScreenView()
                navController.navigate(CharactersRoute)
            },
            onNavigateToLocations = {
                viewModel.logLocationsScreenView()
                navController.navigate(LocationsRoute)
            },
            onNavigateToEpisodes = {
                viewModel.logEpisodesScreenView()
                navController.navigate(EpisodesRoute)
            }
        )
    }
}
