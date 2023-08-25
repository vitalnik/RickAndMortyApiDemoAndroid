package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.R
import com.example.rickandmorty.app.MainViewModel
import com.example.rickandmorty.app.Screen

fun NavGraphBuilder.episodesScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    composable(route = Screen.Episodes.route) {

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

//        val seasons by remember {
//            derivedStateOf {
//                val seasonsList = mutableListOf<TabItem>()
//                seasonsList.add(TabItem(context.getString(R.string.all_seasons), 0))
//                mainViewModel.seasons.forEach {
//                    seasonsList.add(TabItem(context.getString(R.string.season, it), it))
//                }
//                seasonsList
//            }
//        }

        val seasons = remember {
            val seasonsList = mutableListOf<TabItem>()
            seasonsList.add(TabItem(context.getString(R.string.all_seasons), 0))
            repeat(EpisodesViewModel.seasonCount) {
                seasonsList.add(TabItem(context.getString(R.string.season, it + 1), it + 1))
            }
            seasonsList
        }

        val isEmpty by remember {
            derivedStateOf {
                !isLoading && pagingItems.itemCount == 0
            }
        }

        val selectedSeason by viewModel.selectedSeason

        EpisodesScreen(
            pagingItems = pagingItems,
            isLoading = isLoading,
            isEmpty = isEmpty,
            alertDialogVisible = alertDialogVisible,
            onRetry = {
                viewModel.selectedSeason.value = 0
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

                val searchQuery = if (it > 0)
                    "S${it.toString().padStart(2, '0')}" else ""

                mainViewModel.episodeSearchValue.value = searchQuery

                pagingItems.refresh()
            },
            onNavigateToEpisode = {
                navController.navigate(Screen.Episode.createRoute(it.toString()))
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
