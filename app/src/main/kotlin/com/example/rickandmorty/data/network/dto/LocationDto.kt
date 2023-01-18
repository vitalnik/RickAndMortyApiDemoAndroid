package com.example.rickandmorty.data.network.dto

import com.example.rickandmorty.app.utils.extensions.idsList
import com.example.rickandmorty.domain.models.LocationModel
import kotlinx.serialization.Serializable

@Serializable
internal data class LocationDto(
    val id: Int,
    val name: String,
    val created: String,
    val dimension: String,
    val residents: List<String>,
    val type: String,
    val url: String
)

internal fun LocationDto.toDomain() =
    LocationModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residentIds = this.residents.idsList(),
    )
