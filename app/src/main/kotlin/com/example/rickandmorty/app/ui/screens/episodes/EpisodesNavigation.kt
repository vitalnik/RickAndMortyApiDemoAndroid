package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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

        //val viewModel = hiltViewModel<EpisodesViewModel>()

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

        val isEmpty by remember {
            derivedStateOf {
                !isLoading && pagingItems.itemCount == 0
            }
        }

        EpisodesScreen(
            pagingItems = pagingItems,
            isLoading = isLoading,
            isEmpty = isEmpty,
            alertDialogVisible = alertDialogVisible,
            onRetry = {
                pagingItems.refresh()
            },
            onDismiss = {
                alertDialogVisible = false
            },
            onNavigateToEpisode = {
                navController.navigate("$EPISODE_SCREEN_ROUTE?episodeId=$it")
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
