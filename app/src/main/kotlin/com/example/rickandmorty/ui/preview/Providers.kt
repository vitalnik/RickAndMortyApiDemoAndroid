package com.example.rickandmorty.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.rickandmorty.app.data.dto.*
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.models.LocationModel
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CharacterPreviewProvider : PreviewParameterProvider<CharacterDto> {
    override val values: Sequence<CharacterDto>
        get() {
            val characters = Json.decodeFromString<CharactersDto>(Responses().characters)
            return characters.results.asSequence()
        }

}

class CharactersPreviewProvider : PreviewParameterProvider<CharacterModel> {
    override val values: Sequence<CharacterModel>
        get() {
            val characters = Json.decodeFromString<CharactersDto>(Responses().characters)
            return characters.results.map { it.toDomain() }.asSequence()
        }

}


class EpisodePreviewProvider : PreviewParameterProvider<EpisodeDto> {
    override val values: Sequence<EpisodeDto>
        get() {
            val episodes = Json.decodeFromString<EpisodesDto>(Responses().episodes)
            return episodes.results.asSequence()
        }
}

class EpisodesPreviewProvider : PreviewParameterProvider<EpisodeModel> {
    override val values: Sequence<EpisodeModel>
        get() {
            val episodes = Json.decodeFromString<EpisodesDto>(Responses().episodes)
            return episodes.results.map { it.toDomain() }.asSequence()
        }
}

class LocationPreviewProvider : PreviewParameterProvider<LocationDto> {
    override val values: Sequence<LocationDto>
        get() {
            val episodes = Json.decodeFromString<LocationsDto>(Responses().locations)
            return episodes.results.asSequence()
        }
}

class LocationsPreviewProvider : PreviewParameterProvider<LocationModel> {
    override val values: Sequence<LocationModel>
        get() {
            val episodes = Json.decodeFromString<LocationsDto>(Responses().locations)
            return episodes.results.map { it.toDomain() }.asSequence()
        }
}
