package com.example.rickandmorty.app.data.dto

import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.Status.Companion.getStatus
import com.example.rickandmorty.app.utils.extensions.idsList
import kotlinx.serialization.Serializable

@Serializable
data class CharacterDto(
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

fun CharacterDto.toDomain() = CharacterModel(
    id = this.id,
    name = this.name,
    gender = this.gender,
    species = this.species,
    status = this.status.getStatus(),
    type = this.type,
    imageUrl = this.image,
    origin = this.origin,
    location = this.location,
    episodeIds = this.episode.idsList(),
)