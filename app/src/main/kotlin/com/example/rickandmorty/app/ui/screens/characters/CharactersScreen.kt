package com.example.rickandmorty.app.ui.screens.characters

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.common.CharacterImage
import com.example.rickandmorty.app.ui.common.OriginAndLocation
import com.example.rickandmorty.app.ui.common.SearchPanel
import com.example.rickandmorty.app.ui.components.*
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.Status
import rememberLazyListState

@Composable
fun CharactersScreen(
    pagingItems: LazyPagingItems<CharacterModel>,
    isLoading: Boolean = false,
    isEmpty: Boolean = false,
    alertDialogVisible: Boolean = false,
    searchPanelVisible: Boolean = false,
    onSearchIconClick: () -> Unit = {},
    searchValue: String,
    onSearchValueChange: (value: String) -> Unit = {},
    onRetry: () -> Unit = {},
    onDismiss: () -> Unit = {},
    onNavigateToCharacter: (character: CharacterModel) -> Unit = {},
    onBackPress: () -> Unit = {}
) {
    SystemBarsColor()

    val listState = pagingItems.rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    LaunchedEffect(key1 = searchPanelVisible) {
        if (searchPanelVisible) {
            listState.animateScrollBy(-200f)
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = stringResource(id = R.string.characters),
                        icon1 = R.drawable.ic_search,
                        icon2 = R.drawable.ic_refresh,
                        onIcon1Click = {
                            onSearchIconClick()
                        },
                        onIcon2Click = {
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

                stickyHeader {
                    AnimatedVisibility(visible = searchPanelVisible) {
                        SearchPanel(searchValue, onSearchValueChange)
                    }
                }

                items(
                    items = pagingItems,
                    key = {
                        it.id
                    }
                ) { character ->
                    character?.let {
                        CharacterCard(character, onNavigateToCharacter = onNavigateToCharacter)
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
                EmptyState(stringResource(id = R.string.no_characters_found))
            }
            if (alertDialogVisible) {
                RetryDialog(
                    errorMessage = stringResource(id = R.string.error_loading_characters),
                    onRetryClick = onRetry,
                    onDismissClick = onDismiss
                )
            }
        }
    }
}

@Composable
private fun CharacterCard(
    character: CharacterModel,
    onNavigateToCharacter: (character: CharacterModel) -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        onClick = {
            onNavigateToCharacter(character)
        }
    ) {

        Column(modifier = Modifier.padding(all = 16.dp)) {

            Row {

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    CharacterImage(imageUrl = character.imageUrl)

                    VerticalSpacer(8.dp)

                    if (character.status != Status.UNKNOWN) {
                        Text(
                            text = character.status.toString(),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }


                HorizontalSpacer()

                Column {

                    Text(text = character.name, style = MaterialTheme.typography.titleLarge)

                    VerticalSpacer(4.dp)

                    DividerLine()

                    VerticalSpacer(8.dp)

                    OriginAndLocation(character = character)

                    VerticalSpacer(8.dp)

                    Text(
                        text = stringResource(
                            id = R.string.appears_in_episodes,
                            character.episodeIds.count()
                        ),
                        style = MaterialTheme.typography.labelLarge
                    )
                }
            }
        }
    }
}
