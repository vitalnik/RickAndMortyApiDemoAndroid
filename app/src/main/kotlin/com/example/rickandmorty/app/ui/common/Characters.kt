package com.example.rickandmorty.app.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.components.DividerLine
import com.example.rickandmorty.app.ui.components.HorizontalSpacer
import com.example.rickandmorty.app.ui.components.VerticalSpacer
import com.example.rickandmorty.domain.models.CharacterModel

@Composable
fun CharactersListHeader(headerTextId: Int, charactersCount: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(
                id = headerTextId,
                charactersCount
            ),
            style = MaterialTheme.typography.titleLarge
        )
    }
}

fun LazyListScope.charactersList(
    characters: List<CharacterModel>,
    onNavigateToCharacter: (character: CharacterModel) -> Unit
) {
    items(items = characters, key = {
        it.id
    }) { character ->
        CharacterCard(character,
            onNavigateToCharacter = {
                onNavigateToCharacter(character)
            })
    }
    item {
        VerticalSpacer()
    }
}

@Composable
private fun CharacterCard(
    character: CharacterModel,
    onNavigateToCharacter: () -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        onClick = {
            onNavigateToCharacter()
        }
    ) {

        Column(modifier = Modifier.padding(all = 16.dp)) {

            Row {

                CharacterImage(imageUrl = character.imageUrl, size = 80.dp)

                HorizontalSpacer()

                Column {

                    Text(text = character.name, style = MaterialTheme.typography.titleLarge)

                    VerticalSpacer(4.dp)

                    DividerLine()

                    VerticalSpacer(4.dp)

                    Text(
                        text = character.location.name,
                        style = MaterialTheme.typography.titleMedium
                    )

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

