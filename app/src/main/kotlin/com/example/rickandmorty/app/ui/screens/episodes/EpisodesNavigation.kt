package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.app.EPISODES_SCREEN_ROUTE
import com.example.rickandmorty.app.EPISODE_SCREEN_ROUTE
import com.example.rickandmorty.app.MainViewModel
import com.google.accompanist.navigation.animation.composable

fun NavGraphBuilder.episodesScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    composable(route = EPISODES_SCREEN_ROUTE) {

        val viewModel = hiltViewModel<EpisodesViewModel>()

        val context = LocalContext.current

        val pagingItems = mainViewModel.episodesPagingData.collectAsLazyPagingItems()

        var alertDialogVisible by rememberSaveable { mutableStateOf(false) }

        val isLoading by remember {
            derivedStateOf {
                arrayOf(
                    pagingItems.loadState.append,
                    pagingItems.loadState.refresh,
                    pagingItems.loadState.prepend,
                ).any { it is LoadState.Loading }
            }
        }

        LaunchedEffect(
            key1 = pagingItems.loadState.refresh,
            key2 = pagingItems.loadState.append
        ) {
            alertDialogVisible =
                pagingItems.loadState.refresh is LoadState.Error ||
                        pagingItems.loadState.append is LoadState.Error
        }

//        LaunchedEffect(key1 = pagingItems.loadState.append) {
//            if (pagingItems.loadState.append.endOfPaginationReached) {
//                Toast.makeText(
//                    context,
//                    context.getString(R.string.all_episodes_loaded),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

        val isEmpty by remember {
            derivedStateOf {
                !isLoading && pagingItems.itemCount == 0
            }
        }

        val seasons = viewModel.seasons
        val selectedSeason by viewModel.selectedSeason

        EpisodesScreen(
            pagingItems = pagingItems,
            isLoading = isLoading,
            isEmpty = isEmpty,
            alertDialogVisible = alertDialogVisible,
            onRetry = {
                mainViewModel.episodeSearchValue.value = ""
                pagingItems.refresh()
            },
            onDismiss = {
                alertDialogVisible = false
            },
            seasons = seasons,
            selectedSeason = selectedSeason,
            onSeasonSelected = {

                viewModel.selectedSeason.value = it

                val searchQuery =
                    "S${it.split(" ")[1].toIntOrNull()?.toString()?.padStart(2, '0') ?: ""}"
                mainViewModel.episodeSearchValue.value = searchQuery

                pagingItems.refresh()
            },
            onNavigateToEpisode = {
                navController.navigate("$EPISODE_SCREEN_ROUTE?episodeId=$it")
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
