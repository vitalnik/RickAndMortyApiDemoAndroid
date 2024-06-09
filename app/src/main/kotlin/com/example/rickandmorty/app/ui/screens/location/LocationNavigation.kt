package com.example.rickandmorty.app.ui.screens.location

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.app.CharacterRoute
import com.example.rickandmorty.app.HomeRoute
import com.example.rickandmorty.app.LocationRoute
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.locationScreen(
    navController: NavHostController,
) {

    composable<LocationRoute> { backStackEntry ->

        val viewModel = hiltViewModel<LocationViewModel>()

        val locationState by viewModel.locationFlow.collectAsState()
        val charactersState by viewModel.charactersFlow.collectAsState()

        val args = backStackEntry.toRoute<LocationRoute>()

        fun loadLocation() {
            viewModel.getLocation(locationId = args.locationId.toInt())
        }

        LaunchedEffect(key1 = args.locationId) {
            loadLocation()
        }

        LocationScreen(
            locationState = locationState,
            charactersState = charactersState,
            onNavigateToCharacter = {
                navController.navigate(
                    CharacterRoute(
                        characterId = it.id.toString(),
                        characterJson = Json.encodeToString(it)
                    )
                )
            },
            onRetry = {
                loadLocation()
            },
            onBackPress = {
                navController.popBackStack()
            },
            onNavigateHome = {
                navController.popBackStack(HomeRoute, inclusive = false)
            })

    }

}
