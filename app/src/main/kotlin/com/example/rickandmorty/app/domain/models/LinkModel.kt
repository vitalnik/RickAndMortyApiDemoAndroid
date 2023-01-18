package com.example.rickandmorty.app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LinkModel(
    val name: String,
    val url: String
)