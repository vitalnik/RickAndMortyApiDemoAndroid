package com.example.rickandmorty.app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class EpisodeModel(
    val id: Int,
    val name: String,
    val airDate: String,
    val episodeCode: String,
    val characterIds: List<Int>
)