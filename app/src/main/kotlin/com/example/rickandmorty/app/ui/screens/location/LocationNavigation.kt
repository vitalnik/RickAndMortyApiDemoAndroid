package com.example.rickandmorty.app.ui.screens.location

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.CHARACTER_SCREEN_ROUTE
import com.example.rickandmorty.app.HOME_SCREEN_ROUTE
import com.example.rickandmorty.app.LOCATION_SCREEN_ROUTE
import com.google.accompanist.navigation.animation.composable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.locationScreen(
    navController: NavHostController,
) {

    composable(
        route = "$LOCATION_SCREEN_ROUTE?locationId={locationId}", arguments = listOf(
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
                navController.navigate("$CHARACTER_SCREEN_ROUTE?character=$characterJson&characterId=${it.id}")
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HOME_SCREEN_ROUTE, inclusive = false)
            })

    }

}
