package com.example.rickandmorty.app.ui.screens.episode

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.common.CharactersListHeader
import com.example.rickandmorty.app.ui.common.charactersList
import com.example.rickandmorty.app.ui.components.CenteredLoadingIndicator
import com.example.rickandmorty.app.ui.components.ErrorMessage
import com.example.rickandmorty.app.ui.components.NavigationIcon
import com.example.rickandmorty.app.ui.components.SetSystemBarsColor
import com.example.rickandmorty.app.ui.components.TopAppBarRow
import com.example.rickandmorty.app.ui.components.VerticalSpacer
import com.example.rickandmorty.app.ui.preview.CharactersPreviewProvider
import com.example.rickandmorty.app.ui.preview.EpisodesPreviewProvider
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.toErrorMessage
import com.example.rickandmorty.app.utils.withState
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.EpisodeModel

@Composable
fun EpisodeScreen(
    episodeState: ViewState<EpisodeModel>,
    isLoading: Boolean = false,
    charactersState: ViewState<List<CharacterModel>>,
    onNavigateToCharacter: (character: CharacterModel) -> Unit = {},
    onRetry: () -> Unit = {},
    onBackPress: () -> Unit = {},
    onNavigateHome: () -> Unit = {},
) {

    SetSystemBarsColor()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    TopAppBarRow(
                        title = episodeState.withState { it.episodeCode }
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
                ErrorMessage(episodeState.errorCode.toErrorMessage(), onRetry = onRetry)
            }

            if (charactersState is ViewState.Error) {
                ErrorMessage(charactersState.errorCode.toErrorMessage(), onRetry = onRetry)
            }

            if (isLoading) {
                CenteredLoadingIndicator()
            }
        }
    }
}

@Composable
private fun EpisodeHeader(episode: EpisodeModel) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        VerticalSpacer(8.dp)
        Text(text = episode.name, style = MaterialTheme.typography.displaySmall)
        Text(text = episode.airDate, style = MaterialTheme.typography.bodyMedium)
        VerticalSpacer()
    }
}

@Preview(showBackground = true)
@Composable
fun EpisodeScreenPreview() {
    RickAndMortyTheme {
        EpisodeScreen(
            episodeState = ViewState.Populated(EpisodesPreviewProvider().values.first()),
            charactersState = ViewState.Populated(CharactersPreviewProvider().values.toList())
        )
    }
}
