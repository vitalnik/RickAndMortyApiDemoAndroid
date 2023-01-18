package com.example.rickandmorty.app.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class CharactersDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)


