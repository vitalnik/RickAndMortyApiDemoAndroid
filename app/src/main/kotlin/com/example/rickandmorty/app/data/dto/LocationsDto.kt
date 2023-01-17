package com.example.rickandmorty.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationsDto(
    val info: InfoDto,
    val results: List<LocationDto>
)

