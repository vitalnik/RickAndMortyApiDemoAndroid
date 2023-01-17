package com.example.rickandmorty.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class LinkDto(
    val name: String,
    val url: String
)