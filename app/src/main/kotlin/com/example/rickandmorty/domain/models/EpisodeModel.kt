package com.example.rickandmorty.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeCode: String,
    val seasonNumber: Int,
    val characterIds: List<Int>
)
