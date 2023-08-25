package com.example.rickandmorty.app.ui.screens.characterimage

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.rickandmorty.app.Screen

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
