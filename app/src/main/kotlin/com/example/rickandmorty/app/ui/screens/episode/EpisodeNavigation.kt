package com.example.rickandmorty.app.ui.screens.episode

import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.CHARACTER_SCREEN_ROUTE
import com.example.rickandmorty.app.EPISODE_SCREEN_ROUTE
import com.example.rickandmorty.app.HOME_SCREEN_ROUTE
import com.example.rickandmorty.app.utils.ViewState
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.episodeScreen(
    navController: NavHostController,
) {

    composable(
        route = "$EPISODE_SCREEN_ROUTE?episodeId={episodeId}", arguments = listOf(
            navArgument("episodeId") { defaultValue = 1 },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<EpisodeViewModel>()

        val episodeState by viewModel.episodeFlow.collectAsState()
        val charactersState by viewModel.charactersFlow.collectAsState()

        val episodeId = backStackEntry.arguments?.getInt("episodeId", 1)

        fun getEpisode(isPullRefresh: Boolean = false) {
            episodeId?.let {
                viewModel.getEpisode(episodeId = it, isPullRefresh = isPullRefresh)
            }
        }

        LaunchedEffect(key1 = episodeId) {
            getEpisode()
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
                val characterJson = Json.encodeToString(it)
                navController.navigate("$CHARACTER_SCREEN_ROUTE?character=$characterJson&characterId=${it.id}")
            },
//            onRefresh = {
//                getEpisode(isPullRefresh = true)
//            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HOME_SCREEN_ROUTE, inclusive = false)
            })

    }

}
