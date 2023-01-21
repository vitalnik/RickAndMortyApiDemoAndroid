package com.example.rickandmorty.app.ui.screens.location

import androidx.compose.foundation.background
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
import com.example.rickandmorty.app.ui.preview.LocationsPreviewProvider
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.toErrorMessage
import com.example.rickandmorty.app.utils.withState
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.LocationModel

@Composable
fun LocationScreen(
    locationState: ViewState<LocationModel>,
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
                        title = stringResource(id = R.string.location),
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

                locationState.withState { it }?.let { location ->
                    stickyHeader {
                        LocationHeader(location)
                    }
                    item {
                        LocationSubHeader(location)
                    }
                }

                charactersState.withState { it }?.let { characters ->

                    stickyHeader {
                        CharactersListHeader(R.string.location_residents, characters.count())
                    }

                    charactersList(
                        characters = characters,
                        onNavigateToCharacter = { character ->
                            onNavigateToCharacter(character)
                        })
                }

            }

            if (locationState is ViewState.Error) {
                ErrorMessage(text = locationState.errorCode.toErrorMessage(), onRetry = onRetry)
            }

            if (charactersState is ViewState.Error) {
                ErrorMessage(text = charactersState.errorCode.toErrorMessage(), onRetry = onRetry)
            }

            if (locationState is ViewState.Loading || charactersState is ViewState.Loading) {
                CenteredLoadingIndicator()
            }
        }
    }
}

@Composable
private fun LocationHeader(location: LocationModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp)
    ) {
        Text(text = location.name, style = MaterialTheme.typography.displaySmall)
    }
}

@Composable
private fun LocationSubHeader(location: LocationModel) {
    Column {
        Text(
            text = stringResource(id = R.string.dimension),
            style = MaterialTheme.typography.labelSmall
        )
        Text(text = location.dimension, style = MaterialTheme.typography.titleMedium)

        VerticalSpacer(8.dp)

        Text(
            text = stringResource(id = R.string.type),
            style = MaterialTheme.typography.labelSmall
        )
        Text(text = location.type, style = MaterialTheme.typography.titleMedium)

        VerticalSpacer()
    }
}

@Preview(showBackground = true)
@Composable
fun LocationScreenPreview() {
    RickAndMortyTheme {
        LocationScreen(
            locationState = ViewState.Populated(LocationsPreviewProvider().values.first()),
            charactersState = ViewState.Populated(CharactersPreviewProvider().values.toList())
        )
    }
}