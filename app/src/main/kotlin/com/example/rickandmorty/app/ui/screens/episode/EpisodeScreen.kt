package com.example.rickandmorty.app.ui.screens.episode

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
import com.example.rickandmorty.app.ui.common.CharactersListHeader
import com.example.rickandmorty.app.ui.common.charactersList
import com.example.rickandmorty.app.ui.components.*
import com.example.rickandmorty.app.ui.preview.CharactersPreviewProvider
import com.example.rickandmorty.app.ui.preview.EpisodesPreviewProvider
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.withState
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.EpisodeModel

//@OptIn(ExperimentalMaterialApi::class)
@Composable
fun EpisodeScreen(
    episodeState: ViewState<EpisodeModel>,
    isLoading: Boolean = false,
    charactersState: ViewState<List<CharacterModel>>,
    onNavigateToCharacter: (character: CharacterModel) -> Unit = {},
    //  onRefresh: () -> Unit = {},
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

        //val pullRefreshState = rememberPullRefreshState(isLoading, onRefresh)

        Box(
            modifier = Modifier
                //.pullRefresh(pullRefreshState)
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

            //PullRefreshIndicator(isLoading, pullRefreshState, Modifier.align(Alignment.TopCenter))
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