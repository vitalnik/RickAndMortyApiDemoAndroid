package com.example.rickandmorty.domain.models

import kotlinx.serialization.Serializable

enum class Status {
    ALIVE, DEAD, UNKNOWN;

    companion object {
        fun String.getStatus(): Status =
            when (this) {
                "Alive" -> ALIVE
                "Dead" -> DEAD
                else -> UNKNOWN
            }
    }
}

@Serializable
data class CharacterModel(
    val id: Int,
    val name: String,
    val gender: String,
    val species: String,
    val status: Status,
    val type: String,
    val origin: LinkModel,
    val location: LinkModel,
    val imageUrl: String,
    val episodeIds: List<Int>,
)
