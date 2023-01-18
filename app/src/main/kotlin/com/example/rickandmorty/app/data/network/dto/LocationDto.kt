package com.example.rickandmorty.app.data.network.dto

import com.example.rickandmorty.app.domain.models.LocationModel
import com.example.rickandmorty.app.utils.extensions.idsList
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
