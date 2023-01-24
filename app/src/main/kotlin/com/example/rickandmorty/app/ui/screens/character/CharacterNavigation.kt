package com.example.rickandmorty.app.ui.screens.character

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.Screen
import com.example.rickandmorty.data.network.dto.CharacterDto
import com.example.rickandmorty.data.network.dto.toDomain
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.characterScreen(
    navController: NavHostController,
) {

    composable(
        route = Screen.Character.route,
        arguments = listOf(
            navArgument(Screen.Character.characterId) { defaultValue = 1 },
            navArgument(Screen.Character.characterJson) { defaultValue = "" },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<CharacterViewModel>()

        val characterState by viewModel.characterFlow.collectAsStateWithLifecycle()
        val episodesState by viewModel.episodesFlow.collectAsStateWithLifecycle()

        val characterId = backStackEntry.arguments?.getInt(Screen.Character.characterId, 1)
        val characterJson =
            backStackEntry.arguments?.getString(Screen.Character.characterJson) ?: ""

        fun loadCharacter() {
            try {
                if (characterJson.isEmpty()) {
                    throw IllegalArgumentException()
                }
                val character = Json.decodeFromString<CharacterDto>(characterJson)
                viewModel.setCharacter(character.toDomain())
            } catch (e: Exception) {
                characterId?.let {
                    viewModel.getCharacter(characterId = it)
                }
            }
        }

        LaunchedEffect(key1 = characterId) {
            loadCharacter()
        }

        CharacterScreen(
            characterState = characterState,
            episodesState = episodesState,
            onNavigateToEpisode = {
                navController.navigate(Screen.Episode.createRoute(episodeId = it.toString()))
            },
            onNavigateToLocation = {
                navController.navigate(Screen.Location.createRoute(locationId = it.toString()))
            },
            onRetry = {
                loadCharacter()
            },
            onCharacterImageClick = { url ->
                navController.navigate(Screen.CharacterImage.createRoute(imageUrl = url))
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
            }
        )
    }
}

