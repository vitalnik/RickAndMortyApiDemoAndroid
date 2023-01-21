package com.example.rickandmorty.app.ui.screens.episode

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.Screen
import com.example.rickandmorty.app.utils.ViewState
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.episodeScreen(
    navController: NavHostController,
) {

    composable(
        route = Screen.Episode.route, arguments = listOf(
            navArgument("episodeId") { defaultValue = 1 },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<EpisodeViewModel>()

        val episodeState by viewModel.episodeFlow.collectAsStateWithLifecycle()
        val charactersState by viewModel.charactersFlow.collectAsStateWithLifecycle()

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
                navController.navigate(
                    Screen.Character.createRoute(
                        it.id.toString(),
                        characterJson
                    )
                )
            },
//            onRefresh = {
//                getEpisode(isPullRefresh = true)
//            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
            })

    }

}
