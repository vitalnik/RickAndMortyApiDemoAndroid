package com.example.rickandmorty.data.network.dto

import com.example.rickandmorty.app.utils.extensions.idsList
import com.example.rickandmorty.domain.models.EpisodeModel
import kotlinx.serialization.Serializable

@Serializable
internal data class EpisodeDto(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
)

internal fun EpisodeDto.toDomain() =
    EpisodeModel(
        id = this.id,
        name = this.name,
        airDate = this.air_date,
        episodeCode = this.episode,
        seasonNumber = this.episode.substring(1, 3).toInt(),
        characterIds = this.characters.idsList(),
    )
