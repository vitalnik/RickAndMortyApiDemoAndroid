package com.example.rickandmorty.app.ui.screens.episode

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.app.CharacterRoute
import com.example.rickandmorty.app.EpisodeRoute
import com.example.rickandmorty.app.HomeRoute
import com.example.rickandmorty.app.utils.ViewState
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.episodeScreen(
    navController: NavHostController,
) {

    composable<EpisodeRoute> { backStackEntry ->

        val viewModel = hiltViewModel<EpisodeViewModel>()

        val episodeState by viewModel.episodeFlow.collectAsState()
        val charactersState by viewModel.charactersFlow.collectAsState()

        val args = backStackEntry.toRoute<EpisodeRoute>()

        fun loadEpisode() {
            viewModel.getEpisode(episodeId = args.episodeId.toInt())
        }

        LaunchedEffect(key1 = args.episodeId) {
            loadEpisode()
        }

        val isLoading by remember {
            derivedStateOf {
                episodeState is ViewState.Loading || charactersState is ViewState.Loading
            }
        }

        EpisodeScreen(
            episodeState = episodeState,
            charactersState = charactersState,
            isLoading = isLoading,
            onNavigateToCharacter = {
                navController.navigate(
                    CharacterRoute(
                        characterId = it.id.toString(),
                        characterJson = Json.encodeToString(it)
                    )
                )
            },
            onRetry = {
                loadEpisode()
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HomeRoute, inclusive = false)
            })

    }

}
