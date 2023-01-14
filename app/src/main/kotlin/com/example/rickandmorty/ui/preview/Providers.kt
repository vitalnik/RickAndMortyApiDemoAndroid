package com.example.rickandmorty.ui.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.rickandmorty.app.network.dto.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class CharacterPreviewProvider : PreviewParameterProvider<CharacterDTO> {
    override val values: Sequence<CharacterDTO>
        get() {
            val characters = Json.decodeFromString<CharactersDTO>(Responses().characters)
            return characters.results.asSequence()
        }

}

class EpisodePreviewProvider : PreviewParameterProvider<EpisodeDTO> {
    override val values: Sequence<EpisodeDTO>
        get() {
            val episodes = Json.decodeFromString<EpisodesDTO>(Responses().episodes)
            return episodes.results.asSequence()
        }
}

class LocationPreviewProvider : PreviewParameterProvider<LocationDTO> {
    override val values: Sequence<LocationDTO>
        get() {
            val episodes = Json.decodeFromString<LocationsDTO>(Responses().locations)
            return episodes.results.asSequence()
        }
}
