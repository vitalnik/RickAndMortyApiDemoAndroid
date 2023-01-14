package com.example.rickandmorty.app.network.dto

import kotlinx.serialization.Serializable

@Serializable
data class InfoDTO(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)