package com.example.rickandmorty.app.ui.screens.character

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.CHARACTER_SCREEN_ROUTE
import com.example.rickandmorty.app.EPISODE_SCREEN_ROUTE
import com.example.rickandmorty.app.HOME_SCREEN_ROUTE
import com.example.rickandmorty.app.LOCATION_SCREEN_ROUTE
import com.example.rickandmorty.data.network.dto.CharacterDto
import com.example.rickandmorty.data.network.dto.toDomain
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.characterScreen(
    navController: NavHostController,
) {

    composable(
        route = "$CHARACTER_SCREEN_ROUTE?character={character}&characterId={characterId}",
        arguments = listOf(
            navArgument("character") { defaultValue = "" },
            navArgument("characterId") { defaultValue = 1 },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<CharacterViewModel>()

        val characterState by viewModel.characterFlow.collectAsStateWithLifecycle()
        val episodesState by viewModel.episodesFlow.collectAsStateWithLifecycle()

        val characterJson = backStackEntry.arguments?.getString("character") ?: ""
        val characterId = backStackEntry.arguments?.getInt("characterId", 1)

        LaunchedEffect(key1 = characterId) {
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

        CharacterScreen(
            characterState = characterState,
            episodesState = episodesState,
            onNavigateToEpisode = {
                navController.navigate("$EPISODE_SCREEN_ROUTE?episodeId=$it")
            },
            onNavigateToLocation = {
                navController.navigate("$LOCATION_SCREEN_ROUTE?locationId=$it")
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HOME_SCREEN_ROUTE, inclusive = false)
            }
        )
    }
}

