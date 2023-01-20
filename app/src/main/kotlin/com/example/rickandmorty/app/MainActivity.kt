package com.example.rickandmorty.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.rickandmorty.app.ui.screens.character.characterScreen
import com.example.rickandmorty.app.ui.screens.characters.charactersScreen
import com.example.rickandmorty.app.ui.screens.episode.episodeScreen
import com.example.rickandmorty.app.ui.screens.episodes.episodesScreen
import com.example.rickandmorty.app.ui.screens.home.homeScreen
import com.example.rickandmorty.app.ui.screens.location.locationScreen
import com.example.rickandmorty.app.ui.screens.locations.locationsScreen
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val navController = rememberAnimatedNavController()
            RickAndMortyTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    AnimatedNavHost(
                        navController = navController,
                        startDestination = HOME_SCREEN_ROUTE,
                    ) {
                        buildNavigationGraph(navController = navController)
                    }
                }
            }
        }
    }

    private fun NavGraphBuilder.buildNavigationGraph(navController: NavHostController) {
        homeScreen(navController = navController)

        charactersScreen(navController = navController, mainViewModel)
        characterScreen(navController = navController)

        episodesScreen(navController = navController, mainViewModel)
        episodeScreen(navController = navController)

        locationsScreen(navController = navController, mainViewModel)
        locationScreen(navController = navController)
    }

}
