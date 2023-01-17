package com.example.rickandmorty.app.data.dto

import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.utils.extensions.idsList
import kotlinx.serialization.Serializable

@Serializable
data class EpisodeDto(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
)

fun EpisodeDto.toDomain() =
    EpisodeModel(
        id = this.id,
        name = this.name,
        airDate = this.air_date,
        episodeCode = this.episode,
        characterIds = this.characters.idsList(),
    )
