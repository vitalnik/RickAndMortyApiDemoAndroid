package com.example.rickandmorty.app.ui.screens.character

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.app.CharacterImageRoute
import com.example.rickandmorty.app.CharacterRoute
import com.example.rickandmorty.app.EpisodeRoute
import com.example.rickandmorty.app.HomeRoute
import com.example.rickandmorty.app.LocationRoute
import com.example.rickandmorty.domain.models.CharacterModel
import kotlinx.serialization.json.Json

fun NavGraphBuilder.characterScreen(
    navController: NavHostController,
) {

    composable<CharacterRoute> { backStackEntry ->

        val viewModel = hiltViewModel<CharacterViewModel>()

        val characterState by viewModel.characterFlow.collectAsState()
        val episodesState by viewModel.episodesFlow.collectAsState()

        val args = backStackEntry.toRoute<CharacterRoute>()

        fun loadCharacter() {
            try {
                viewModel.setCharacter(
                    Json.decodeFromString<CharacterModel>(args.characterJson)
                )
            } catch (e: Exception) {
                viewModel.getCharacter(characterId = args.characterId.toInt())
            }
        }

        LaunchedEffect(key1 = args.characterId) {
            loadCharacter()
        }

        CharacterScreen(
            characterState = characterState,
            episodesState = episodesState,
            onNavigateToEpisode = {
                navController.navigate(
                    EpisodeRoute(episodeId = it.toString())
                )
            },
            onNavigateToLocation = {
                navController.navigate(
                    LocationRoute(locationId = it.toString())
                )
            },
            onRetry = {
                loadCharacter()
            },
            onCharacterImageClick = { url ->
                navController.navigate(
                    CharacterImageRoute(
                        imageUrl = url
                    )
                )
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HomeRoute, inclusive = false)
            }
        )
    }
}
