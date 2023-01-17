package com.example.rickandmorty.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CharactersDto(
    val info: InfoDto,
    val results: List<CharacterDto>
)


