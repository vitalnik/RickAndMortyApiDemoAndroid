package com.example.rickandmorty.app.ui.screens.characterimage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.components.SetSystemBarsColor
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme
import com.example.rickandmorty.app.utils.extensions.clickableWithRipple

@Composable
fun CharacterImageScreen(
    imageUrl: String,
    onClose: () -> Unit,
) {

    SetSystemBarsColor()

    Box(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface),
    ) {

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Fit,
                loading = {
                    Box(modifier = Modifier.size(32.dp), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                },
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 24.dp, top = 20.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                painter = painterResource(
                    id = R.drawable.ic_close
                ),
                contentDescription = stringResource(id = R.string.close),
                modifier = Modifier
                    .clickableWithRipple {
                        onClose()
                    }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterImageScreenPreview() {
    RickAndMortyTheme {
        CharacterImageScreen(
            imageUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            onClose = {}
        )
    }
}