package com.example.rickandmorty.app.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.components.HomeButton
import com.example.rickandmorty.app.ui.components.VerticalSpacer
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme

@Composable
fun HomeScreen(
    onNavigateToEpisodes: () -> Unit = {},
    onNavigateToLocations: () -> Unit = {},
    onNavigateToCharacters: () -> Unit = {},
) {

    Box {
        Image(
            painter = painterResource(id = R.drawable.rickandmorty),
            contentDescription = stringResource(id = R.string.app_name),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier.weight(5f, true)
            )

            HomeButton(text = stringResource(id = R.string.characters), onNavigateToCharacters)

            VerticalSpacer()

            HomeButton(
                text = stringResource(id = R.string.locations),
                onClick = onNavigateToLocations
            )

            VerticalSpacer()

            HomeButton(
                text = stringResource(id = R.string.episodes),
                onClick = onNavigateToEpisodes
            )

            Spacer(
                modifier = Modifier.weight(1f, true)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    RickAndMortyTheme {
        HomeScreen()
    }
}