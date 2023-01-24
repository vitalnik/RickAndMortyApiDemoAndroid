package com.example.rickandmorty.app.ui.screens.characterimage

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.rickandmorty.app.Screen
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.characterImageScreen(
    navController: NavHostController,
) {

    composable(
        route = Screen.CharacterImage.route,
        arguments = listOf(
            navArgument(Screen.CharacterImage.imageUrl) { defaultValue = "" },
        )
    ) { backStackEntry ->

        val viewModel = hiltViewModel<CharacterImageViewModel>()

        val imageUrl = backStackEntry.arguments?.getString(Screen.CharacterImage.imageUrl, "") ?: ""

        CharacterImageScreen(
            imageUrl = imageUrl,
            onClose = {
                navController.popBackStack()
            }
        )
    }
}

