package com.example.rickandmorty.app.ui.screens.location

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.Screen
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.locationScreen(
    navController: NavHostController,
) {

    composable(
        route = Screen.Location.route, arguments = listOf(
            navArgument("locationId") { defaultValue = 1 },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<LocationViewModel>()

        val locationState by viewModel.locationFlow.collectAsStateWithLifecycle()
        val charactersState by viewModel.charactersFlow.collectAsStateWithLifecycle()

        val locationId = backStackEntry.arguments?.getInt("locationId", 1)

        LaunchedEffect(key1 = locationId) {
            locationId?.let {
                viewModel.getLocation(locationId = it)
            }
        }

        LocationScreen(
            locationState = locationState,
            charactersState = charactersState,
            onNavigateToCharacter = {
                val characterJson = Json.encodeToString(it)
                navController.navigate(
                    Screen.Character.createRoute(
                        it.id.toString(),
                        characterJson
                    )
                )
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(Screen.Home.route, inclusive = false)
            })

    }

}
