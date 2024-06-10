package com.example.rickandmorty.app

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.rickandmorty.app.ui.screens.character.characterScreen
import com.example.rickandmorty.app.ui.screens.characterimage.characterImageScreen
import com.example.rickandmorty.app.ui.screens.characters.charactersScreen
import com.example.rickandmorty.app.ui.screens.episode.episodeScreen
import com.example.rickandmorty.app.ui.screens.episodes.episodesScreen
import com.example.rickandmorty.app.ui.screens.home.homeScreen
import com.example.rickandmorty.app.ui.screens.location.locationScreen
import com.example.rickandmorty.app.ui.screens.locations.locationsScreen
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme

@Composable
fun RickAndMortyApp() {
    val navController = rememberNavController()

    val mainViewModel = hiltViewModel<MainViewModel>()

    RickAndMortyTheme {
        NavHost(
            navController = navController,
            startDestination = HomeRoute,
        ) {
            buildNavigationGraph(
                navController = navController,
                mainViewModel = mainViewModel
            )
        }
    }
}

private fun NavGraphBuilder.buildNavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    homeScreen(navController = navController)

    charactersScreen(navController = navController, mainViewModel)
    characterScreen(navController = navController)
    characterImageScreen(navController = navController)

    episodesScreen(navController = navController, mainViewModel)
    episodeScreen(navController = navController)

    locationsScreen(navController = navController, mainViewModel)
    locationScreen(navController = navController)
}
