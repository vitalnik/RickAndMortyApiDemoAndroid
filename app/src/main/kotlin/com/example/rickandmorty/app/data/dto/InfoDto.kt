package com.example.rickandmorty.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class InfoDto(
    val count: Int,
    val next: String?,
    val pages: Int,
    val prev: String?
)