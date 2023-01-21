package com.example.rickandmorty.app.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.theme.RickAndMortyTheme

@Composable
fun ErrorMessage(text: String, onRetry: (() -> Unit)? = null) {
    Centered {
        Spacer(Modifier.weight(1f, true))
        Text(
            text = text,
            style = MaterialTheme.typography.headlineSmall.copy(textAlign = TextAlign.Center)
        )
        onRetry?.let {
            VerticalSpacer()
            TextButton(onClick = {
                it.invoke()
            }) {
                Text(text = stringResource(id = R.string.try_again))
            }
        }

        Spacer(Modifier.weight(2f, true))
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorMessagePreview() {
    RickAndMortyTheme {
        ErrorMessage(text = "An unknown error has occurred.", onRetry = {})
    }
}