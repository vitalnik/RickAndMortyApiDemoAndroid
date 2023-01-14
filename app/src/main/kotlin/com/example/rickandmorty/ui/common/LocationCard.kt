package com.example.rickandmorty.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.app.network.dto.LocationDTO
import com.example.rickandmorty.ui.components.DividerLine
import com.example.rickandmorty.ui.components.VerticalSpacer
import com.example.rickandmorty.ui.preview.LocationPreviewProvider
import com.example.rickandmorty.ui.theme.RickAndMortyTheme

@Composable
fun LocationCard(
    location: LocationDTO,
    onNavigateToLocation: (() -> Unit)? = null
) {
    ElevatedCard(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        onClick = {
            onNavigateToLocation?.invoke()
        }
    ) {
        Column(modifier = Modifier.padding(all = 16.dp)) {

            Text(text = location.name, style = MaterialTheme.typography.titleLarge)

            VerticalSpacer(4.dp)

            DividerLine()

            VerticalSpacer(8.dp)

            Row {

                Column(Modifier.weight(2f, true)) {
                    Text(
                        text = stringResource(id = R.string.dimension),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(text = location.dimension, style = MaterialTheme.typography.titleMedium)

                }

                Column(Modifier.weight(1f, true)) {
                    Text(
                        text = stringResource(id = R.string.type),
                        style = MaterialTheme.typography.labelSmall
                    )
                    Text(text = location.type, style = MaterialTheme.typography.titleMedium)
                }

            }

            VerticalSpacer(8.dp)

            Text(
                text = stringResource(
                    id = R.string.number_of_residents,
                    location.residents.count()
                ),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationCardPreview() {
    RickAndMortyTheme {
        LocationCard(location = LocationPreviewProvider().values.elementAt(2))
    }
}
