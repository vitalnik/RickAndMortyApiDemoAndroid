package com.example.rickandmorty.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.rickandmorty.app.data.network.dto.CharactersDto
import com.example.rickandmorty.app.data.network.dto.EpisodesDto
import com.example.rickandmorty.app.data.network.dto.LocationsDto
import com.example.rickandmorty.app.data.network.dto.toDomain
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.models.LocationModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CharactersPreviewProvider : PreviewParameterProvider<CharacterModel> {
    override val values: Sequence<CharacterModel>
        get() {
            val characters = Json.decodeFromString<CharactersDto>(Responses().characters)
            return characters.results.map { it.toDomain() }.asSequence()
        }

}

class EpisodesPreviewProvider : PreviewParameterProvider<EpisodeModel> {
    override val values: Sequence<EpisodeModel>
        get() {
            val episodes = Json.decodeFromString<EpisodesDto>(Responses().episodes)
            return episodes.results.map { it.toDomain() }.asSequence()
        }
}

class LocationsPreviewProvider : PreviewParameterProvider<LocationModel> {
    override val values: Sequence<LocationModel>
        get() {
            val episodes = Json.decodeFromString<LocationsDto>(Responses().locations)
            return episodes.results.map { it.toDomain() }.asSequence()
        }
}
