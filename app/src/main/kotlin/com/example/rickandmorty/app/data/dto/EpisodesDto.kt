package com.example.rickandmorty.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class EpisodesDto(
    val info: InfoDto,
    val results: List<EpisodeDto>
)
