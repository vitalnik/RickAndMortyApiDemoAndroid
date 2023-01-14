package com.example.rickandmorty.ui.screens.episode

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.network.dto.CharacterDTO
import com.example.rickandmorty.app.network.dto.EpisodeDTO
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.withState
import com.example.rickandmorty.ui.common.CharactersListHeader
import com.example.rickandmorty.ui.common.charactersList
import com.example.rickandmorty.ui.components.*
import com.example.rickandmorty.ui.preview.CharacterPreviewProvider
import com.example.rickandmorty.ui.preview.EpisodePreviewProvider
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun EpisodeScreen(
    episodeState: ViewState<EpisodeDTO>,
    isLoading: Boolean = false,
    charactersState: ViewState<List<CharacterDTO>>,
    onNavigateToCharacter: (character: CharacterDTO) -> Unit = {},
    onBackPress: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
) {

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = episodeState.withState { it.episode }
                            ?: stringResource(id = R.string.episode),
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
                    end = 16.dp
                )
        ) {

            val listState = rememberLazyListState()

            LazyColumn(state = listState) {

                episodeState.withState { it }?.let { episode ->
                    stickyHeader {
                        EpisodeHeader(episode)
                    }
                }

                charactersState.withState { it }?.let { characters ->

                    stickyHeader {
                        CharactersListHeader(R.string.episode_characters, characters.count())
                    }

                    charactersList(
                        characters = characters,
                        onNavigateToCharacter = { character ->
                            onNavigateToCharacter(character)
                        })
                }
            }

            if (episodeState is ViewState.Error) {
                ErrorMessage(episodeState.errorMessage)
            }

            if (charactersState is ViewState.Error) {
                ErrorMessage(charactersState.errorMessage)
            }

            if (isLoading) {
                CenteredLoadingIndicator()
            }
        }
    }
}

@Composable
private fun EpisodeHeader(episode: EpisodeDTO) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        VerticalSpacer(8.dp)
        Text(text = episode.name, style = MaterialTheme.typography.displaySmall)
        Text(text = episode.air_date, style = MaterialTheme.typography.bodyMedium)
        VerticalSpacer()
    }
}

@Preview(showBackground = true)
@Composable
fun EpisodeScreenPreview() {
    RickAndMortyTheme {
        EpisodeScreen(
            episodeState = ViewState.Populated(EpisodePreviewProvider().values.first()),
            charactersState = ViewState.Populated(CharacterPreviewProvider().values.toList())
        )
    }
}