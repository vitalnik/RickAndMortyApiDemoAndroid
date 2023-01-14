package com.example.rickandmorty.app.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class LocationsDTO(
    val info: InfoDTO,
    val results: List<LocationDTO>
)

@Serializable
data class LocationDTO(
    val id: Int,
    val name: String,
    val created: String,
    val dimension: String,
    val residents: List<String>,
    val type: String,
    val url: String
)