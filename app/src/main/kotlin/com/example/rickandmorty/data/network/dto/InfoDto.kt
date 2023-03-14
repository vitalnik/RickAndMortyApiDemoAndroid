package com.example.rickandmorty.data.network.dto

import kotlinx.serialization.Serializable

@Serializable
internal data class InfoDto(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)
