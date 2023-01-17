package com.example.rickandmorty.app.domain.models

import com.example.rickandmorty.app.data.dto.LinkDto
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
    val origin: LinkDto,
    val location: LinkDto,
    val imageUrl: String,
    val episodeIds: List<Int>,
)