package com.example.rickandmorty.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.network.dto.EpisodeDTO
import com.example.rickandmorty.ui.components.DividerLine
import com.example.rickandmorty.ui.components.VerticalSpacer

@Composable
fun EpisodeCard(
    episode: EpisodeDTO,
    onNavigateToEpisode: () -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        onClick = {
            onNavigateToEpisode()
        }
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {

            Row {
                Text(text = episode.air_date, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.weight(1f, true))
                Text(text = episode.episode, style = MaterialTheme.typography.titleSmall)
            }

            VerticalSpacer(4.dp)

            DividerLine()

            VerticalSpacer(8.dp)

            Text(text = episode.name, style = MaterialTheme.typography.titleLarge)

            VerticalSpacer(8.dp)

            Text(
                text = stringResource(
                    id = R.string.number_of_characters,
                    episode.characters.count()
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
