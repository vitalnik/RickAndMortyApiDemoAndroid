package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
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

@Composable
fun EpisodesScreen(
    pagingItems: LazyPagingItems<EpisodeModel>,
    isLoading: Boolean,
    isEmpty: Boolean,
    alertDialogVisible: Boolean,
    onRetry: () -> Unit,
    onDismiss: () -> Unit,
    seasons: List<String>,
    selectedSeason: String,
    onSeasonSelected: (season: String) -> Unit,
    onNavigateToEpisode: (episodeId: Int) -> Unit,
    onBackPress: () -> Unit
) {

    SystemBarsColor()

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
                )
        ) {

            LazyColumn(state = listState) {

                stickyHeader {
                    SeasonTabs(seasons = seasons,
                        selectedSeason = selectedSeason,
                        onSeasonSelected = {
                            onSeasonSelected(it)
                        })
                }

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

@Composable
private fun SeasonTabs(
    seasons: List<String>,
    selectedSeason: String,
    onSeasonSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = seasons.indexOfFirst { it == selectedSeason }

    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {

        ScrollableTabRow(
            selectedTabIndex = selectedIndex,
            divider = {}, /* Disable the built-in divider */
            edgePadding = 24.dp,
            indicator = {},
            modifier = modifier
        ) {
            seasons.forEachIndexed { index, season ->
                Tab(
                    selected = index == selectedIndex,
                    onClick = { onSeasonSelected(season) },
                    modifier.padding(horizontal = 4.dp)
                ) {
                    ChoiceChipContent(text = season, selected = index == selectedIndex)
                }
            }
        }
    }

}

@Composable
private fun ChoiceChipContent(
    text: String,
    selected: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        color = when {
            selected -> MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
            else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        },
        contentColor = when {
            selected -> MaterialTheme.colorScheme.primary
            else -> MaterialTheme.colorScheme.onSurface
        },
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
    }
}

