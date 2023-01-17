package com.example.rickandmorty.app.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class LocationModel(
    val id: Int,
    val name: String,
    val dimension: String,
    val type: String,
    val residentIds: List<Int>
)
