package com.example.rickandmorty.app.ui.screens.locations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.rickandmorty.app.MainViewModel
import com.example.rickandmorty.app.Screen
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun NavGraphBuilder.locationsScreen(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {

    composable(route = Screen.Locations.route) {

        //val context = LocalContext.current

        val scope = rememberCoroutineScope()
        var refreshJob by remember { mutableStateOf<Job?>(Job()) }

        //val viewModel = hiltViewModel<LocationsViewModel>()

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

//        LaunchedEffect(key1 = pagingItems.loadState.append) {
//            if (pagingItems.loadState.append.endOfPaginationReached) {
//                Toast.makeText(
//                    context,
//                    context.getString(R.string.all_locations_loaded),
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }

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
                navController.navigate(Screen.Location.createRoute(it.toString()))
            },
            onBackPress = {
                navController.popBackStack()
            })

    }

}
