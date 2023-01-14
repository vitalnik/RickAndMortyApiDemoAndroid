package com.example.rickandmorty.ui.screens.locations

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.LOCATIONS_SCREEN_ROUTE
import com.example.rickandmorty.LOCATION_SCREEN_ROUTE
import com.example.rickandmorty.MainViewModel
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.locationsScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    composable(route = LOCATIONS_SCREEN_ROUTE) {

        val scope = rememberCoroutineScope()
        var refreshJob by remember { mutableStateOf<Job?>(Job()) }

        val viewModel = hiltViewModel<LocationsViewModel>()

        val pagingItems = mainViewModel.locationsPagingData.collectAsLazyPagingItems()

        val searchValue by mainViewModel.locationNameSearchValue

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

        LocationsScreen(
            pagingItems = pagingItems,
            isLoading = isLoading,
            isEmpty = isEmpty,
            alertDialogVisible = alertDialogVisible,
            searchValue = searchValue,
            onSearchValueChange = {
                mainViewModel.locationNameSearchValue.value = it
                refreshJob?.cancel()
                refreshJob = scope.launch {
                    delay(750)
                    pagingItems.refresh()
                }
            },
            onRetry = {
                pagingItems.refresh()
            },
            onDismiss = {
                alertDialogVisible = false
            },
            onNavigateToLocation = {
                navController.navigate("$LOCATION_SCREEN_ROUTE?locationId=$it")
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
