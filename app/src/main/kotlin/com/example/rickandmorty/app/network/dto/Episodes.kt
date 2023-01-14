package com.example.rickandmorty.app.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class EpisodesDTO(
    val info: InfoDTO,
    val results: List<EpisodeDTO>
)

@Serializable
data class EpisodeDTO(
    val id: Int,
    val name: String,
    val air_date: String,
    val episode: String,
    val characters: List<String>,
    val url: String,
    val created: String,
)