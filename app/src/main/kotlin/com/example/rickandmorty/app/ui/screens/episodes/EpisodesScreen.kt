package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.common.EpisodeCard
import com.example.rickandmorty.app.ui.components.*
import com.example.rickandmorty.domain.models.EpisodeModel
import rememberLazyListState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodesScreen(
    pagingItems: LazyPagingItems<EpisodeModel>,
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    alertDialogVisible: Boolean = false,
    onRetry: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onNavigateToEpisode: (episodeId: Int) -> Unit = {},
    onBackPress: () -> Unit = {}
) {

    val listState = pagingItems.rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = stringResource(id = R.string.episodes),
                        icon1 = R.drawable.ic_refresh,
                        onIcon1Click = {
                            onRetry()
                        }
                    )
                },
                navigationIcon = {
                    NavigationIcon {
                        onBackPress()
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }) { scaffoldPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = scaffoldPadding.calculateTopPadding(),
                    start = 16.dp,
                    end = 16.dp
                )
        ) {

            LazyColumn(state = listState) {

                items(
                    items = pagingItems,
                    key = {
                        it.id
                    }
                ) { episode ->
                    episode?.let {
                        EpisodeCard(
                            episode = it,
                            onNavigateToEpisode = {
                                onNavigateToEpisode(it.id)
                            })
                    }
                }

                item {
                    VerticalSpacer()
                }
            }

            if (isLoading) {
                CenteredLoadingIndicator()
            }

            if (isEmpty) {
                EmptyState(stringResource(id = R.string.no_episodes_found))
            }

            if (alertDialogVisible) {
                RetryDialog(
                    errorMessage = stringResource(id = R.string.error_loading_episodes),
                    onRetryClick = onRetry,
                    onDismissClick = onDismiss
                )
            }
        }
    }
}

