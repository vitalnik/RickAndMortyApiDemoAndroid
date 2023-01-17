package com.example.rickandmorty.ui.screens.character

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.getIdFromUrl
import com.example.rickandmorty.app.utils.withState
import com.example.rickandmorty.ui.common.CharacterImage
import com.example.rickandmorty.ui.common.EpisodeCard
import com.example.rickandmorty.ui.components.*
import com.example.rickandmorty.ui.preview.CharactersPreviewProvider
import com.example.rickandmorty.ui.preview.EpisodesPreviewProvider
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun CharacterScreen(
    characterState: ViewState<CharacterModel>,
    episodesState: ViewState<List<EpisodeModel>>,
    onNavigateToEpisode: (episodeId: Int) -> Unit = {},
    onNavigateToLocation: (locationId: Int) -> Unit = {},
    onBackPress: () -> Unit = {},
    onNavigateHome: () -> Unit = {}
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = if (characterState is ViewState.Loading ||
                            episodesState is ViewState.Loading
                        ) stringResource(id = R.string.loading) else "",
                        icon1 = R.drawable.ic_home,
                        onIcon1Click = {
                            onNavigateHome()
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
                    end = 16.dp,
                )
        ) {

            val listState = rememberLazyListState()

            LazyColumn(state = listState) {

                characterState.withState { it }?.let { character ->

                    item {
                        CharacterHeader(character = character)
                    }

                    stickyHeader {
                        CharacterSubHeader(character = character)
                    }

                    item {
                        OriginAndLocationCards(
                            character = character,
                            onNavigateToLocation = onNavigateToLocation
                        )
                        VerticalSpacer()
                    }
                }

                if (episodesState is ViewState.Populated) {
                    stickyHeader {
                        EpisodeListHeader(episodesState)
                    }

                    episodesState.withState { it }?.let { episodes ->
                        episodesList(episodes,
                            onNavigateToEpisode = {
                                onNavigateToEpisode(it)
                            })
                    }
                }

                item {
                    if (episodesState is ViewState.Error) {
                        ErrorMessage(episodesState.errorMessage)
                    }
                }
            }

            if (characterState is ViewState.Error) {
                ErrorMessage(text = characterState.errorMessage)
            }

            if (characterState is ViewState.Loading || episodesState is ViewState.Loading) {
                CenteredLoadingIndicator()
            }
        }
    }
}

@Composable
private fun EpisodeListHeader(episodesState: ViewState<List<EpisodeModel>>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                id = R.string.appears_in_episodes,
                episodesState.withState { it }?.count() ?: 0
            ),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun CharacterHeader(
    character: CharacterModel
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row {

            CharacterImage(imageUrl = character.imageUrl, size = 200.dp, cornerRadius = 16.dp)

            HorizontalSpacer()

            Column {
                VerticalSpacer()
                Text(
                    text = stringResource(id = R.string.species),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = character.species,
                    style = MaterialTheme.typography.titleMedium
                )
                VerticalSpacer(8.dp)
                Text(
                    text = stringResource(id = R.string.gender),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = character.gender,
                    style = MaterialTheme.typography.titleMedium
                )
                VerticalSpacer(8.dp)
                Text(
                    text = stringResource(id = R.string.status),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(
                    text = character.status.toString(),
                    style = MaterialTheme.typography.titleMedium
                )

            }
        }

    }
}

@Composable
private fun CharacterSubHeader(
    character: CharacterModel,
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {

        VerticalSpacer()

        Text(text = character.name, style = MaterialTheme.typography.displaySmall)

        VerticalSpacer()
    }
}

@Composable
fun OriginAndLocationCards(
    character: CharacterModel,
    onNavigateToLocation: (locationId: Int) -> Unit = {}
) {
    Row {

        ElevatedCard(
            enabled = character.origin.url.isNotEmpty(),
            onClick = {
                onNavigateToLocation(character.origin.url.getIdFromUrl())
            },
            modifier = Modifier.weight(1f, true)
        ) {

            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.origin),
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(8.dp)
                Text(
                    text = character.origin.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.height(40.dp)
                )
            }

        }

        HorizontalSpacer(16.dp)

        ElevatedCard(
            enabled = character.location.url.isNotEmpty(),
            onClick = { onNavigateToLocation(character.location.url.getIdFromUrl()) },
            modifier = Modifier.weight(1f, true)
        )
        {
            Column(modifier = Modifier.padding(all = 8.dp)) {
                Text(
                    text = stringResource(id = R.string.location),
                    style = MaterialTheme.typography.labelSmall
                )
                VerticalSpacer(8.dp)
                Text(
                    text = character.location.name,
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.height(40.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OriginAndLocationCardsPreview() {
    RickAndMortyTheme {
        OriginAndLocationCards(
            character = CharactersPreviewProvider().values.first(),
        )
    }
}

fun LazyListScope.episodesList(
    episodes: List<EpisodeModel>,
    onNavigateToEpisode: (episodeId: Int) -> Unit = {}
) {

    items(items = episodes, key = {
        it.id
    }) { episode ->
        EpisodeCard(episode, onNavigateToEpisode = {
            onNavigateToEpisode(episode.id)
        })
    }
    item {
        VerticalSpacer()
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterScreenPreview() {
    RickAndMortyTheme {
        CharacterScreen(
            characterState = ViewState.Populated(CharactersPreviewProvider().values.first()),
            episodesState = ViewState.Populated(EpisodesPreviewProvider().values.toList())
        )
    }
}