package com.example.rickandmorty.app.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharactersDTO(
    val info: InfoDTO,
    val results: List<CharacterDTO>
)

@Serializable
data class CharacterDTO(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LinkDTO,
    val name: String,
    val origin: LinkDTO,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)

@Serializable
data class LinkDTO(
    val name: String,
    val url: String
)
