package com.example.rickandmorty.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class EpisodesDto(
    val info: InfoDto,
    val results: List<EpisodeDto>
)
