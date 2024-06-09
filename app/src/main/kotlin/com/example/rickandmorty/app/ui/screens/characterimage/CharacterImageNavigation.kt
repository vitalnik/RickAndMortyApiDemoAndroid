package com.example.rickandmorty.app.ui.screens.characterimage

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.rickandmorty.app.CharacterImageRoute

fun NavGraphBuilder.characterImageScreen(
    navController: NavHostController,
) {

    composable<CharacterImageRoute> { backStackEntry ->

        val viewModel = hiltViewModel<CharacterImageViewModel>()

        val imageUrl = backStackEntry.toRoute<CharacterImageRoute>().imageUrl

        CharacterImageScreen(
            imageUrl = imageUrl,
            onClose = {
                navController.popBackStack()
            }
        )
    }
}
