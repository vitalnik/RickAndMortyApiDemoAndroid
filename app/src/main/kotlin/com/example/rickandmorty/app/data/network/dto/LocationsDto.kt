package com.example.rickandmorty.app.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class LocationsDto(
    val info: InfoDto,
    val results: List<LocationDto>
)

