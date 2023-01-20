package com.example.rickandmorty.app.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.ui.components.DividerLine
import com.example.rickandmorty.app.ui.components.VerticalSpacer
import com.example.rickandmorty.domain.models.EpisodeModel

@Composable
fun EpisodeCard(
    episode: EpisodeModel,
    onNavigateToEpisode: () -> Unit
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp, horizontal = 16.dp),
        onClick = {
            onNavigateToEpisode()
        }
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {

            Row {
                Text(text = episode.airDate, style = MaterialTheme.typography.titleSmall)
                Spacer(Modifier.weight(1f, true))
                Text(text = episode.episodeCode, style = MaterialTheme.typography.titleSmall)
            }

            VerticalSpacer(4.dp)

            DividerLine()

            VerticalSpacer(8.dp)

            Text(text = episode.name, style = MaterialTheme.typography.titleLarge)

            VerticalSpacer(8.dp)

            Text(
                text = stringResource(
                    id = R.string.number_of_characters,
                    episode.characterIds.count()
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}
