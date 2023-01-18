package com.example.rickandmorty.data.network.dto

import com.example.rickandmorty.domain.models.LinkModel
import kotlinx.serialization.Serializable

@Serializable
internal data class LinkDto(
    val name: String,
    val url: String
)

internal fun LinkDto.toDomain() = LinkModel(name = this.name, url = this.url)