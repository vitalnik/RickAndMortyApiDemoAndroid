package com.example.rickandmorty.data.network.dto

import com.example.rickandmorty.app.utils.extensions.idsList
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.Status.Companion.getStatus
import kotlinx.serialization.Serializable

@Serializable
internal data class CharacterDto(
    val id: Int,
    val name: String,
    val gender: String,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
    val image: String,
    val location: LinkDto,
    val origin: LinkDto,
    val episode: List<String>,
    val created: String,
)

internal fun CharacterDto.toDomain() = CharacterModel(
    id = this.id,
    name = this.name,
    gender = this.gender,
    species = this.species,
    status = this.status.getStatus(),
    type = this.type,
    imageUrl = this.image,
    origin = this.location.toDomain(),
    location = this.location.toDomain(),
    episodeIds = this.episode.idsList(),
)