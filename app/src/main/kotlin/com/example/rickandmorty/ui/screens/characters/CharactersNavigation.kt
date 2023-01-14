package com.example.rickandmorty.ui.screens.characters

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.CHARACTERS_SCREEN_ROUTE
import com.example.rickandmorty.CHARACTER_SCREEN_ROUTE
import com.example.rickandmorty.MainViewModel
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun NavGraphBuilder.charactersScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    composable(route = CHARACTERS_SCREEN_ROUTE) {

        val scope = rememberCoroutineScope()
        var refreshJob by remember { mutableStateOf<Job?>(Job()) }

        val viewModel = hiltViewModel<CharactersViewModel>()

        val searchPanelVisible by viewModel.searchPanelVisible

        val pagingItems = mainViewModel.charactersPagingData.collectAsLazyPagingItems()
        val searchValue by mainViewModel.characterNameSearchValue

        val isLoading by remember {
            derivedStateOf {
                arrayOf(
                    pagingItems.loadState.append,
                    pagingItems.loadState.refresh,
                    pagingItems.loadState.prepend,
                ).any { it is LoadState.Loading }
            }
        }

        var alertDialogVisible by rememberSaveable { mutableStateOf(false) }

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

        LaunchedEffect(key1 = true) {
            if (searchValue.isNotEmpty()) {
                viewModel.showSearchPanel()
            }
        }

        fun refreshPagingItems(shouldDelay: Boolean = true) {
            refreshJob?.cancel()
            refreshJob = scope.launch {
                if (shouldDelay) {
                    delay(750)
                }
                pagingItems.refresh()
            }
        }

        CharactersScreen(
            pagingItems = pagingItems,
            isLoading = isLoading,
            isEmpty = isEmpty,
            alertDialogVisible = alertDialogVisible,
            searchPanelVisible = searchPanelVisible,
            onSearchIconClick = {
                if (searchPanelVisible) {
                    viewModel.hideSearchPanel()
                } else {
                    viewModel.showSearchPanel()
                }
            },
            searchValue = searchValue,
            onSearchValueChange = {
                mainViewModel.characterNameSearchValue.value = it
                refreshPagingItems()
            },
            onRetry = {
                pagingItems.retry()
            },
            onDismiss = {
                alertDialogVisible = false
            },
            onNavigateToCharacter = {
                val characterJson = Json.encodeToString(it)
                navController.navigate("$CHARACTER_SCREEN_ROUTE?character=$characterJson&characterId=${it.id}")
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
