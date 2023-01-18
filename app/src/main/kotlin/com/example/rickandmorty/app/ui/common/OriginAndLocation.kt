package com.example.rickandmorty.app.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.components.VerticalSpacer
import com.example.rickandmorty.domain.models.CharacterModel

@Composable
fun OriginAndLocation(character: CharacterModel) {
    Column {
        Text(
            text = stringResource(id = R.string.origin),
            style = MaterialTheme.typography.labelSmall
        )
        Text(text = character.origin.name, style = MaterialTheme.typography.titleMedium)
        VerticalSpacer(8.dp)
        Text(
            text = stringResource(id = R.string.location),
            style = MaterialTheme.typography.labelSmall
        )
        Text(text = character.location.name, style = MaterialTheme.typography.titleMedium)
    }
}