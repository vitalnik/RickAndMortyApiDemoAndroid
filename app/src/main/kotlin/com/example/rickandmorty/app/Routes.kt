package com.example.rickandmorty.app

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object CharactersRoute

@Serializable
data class CharacterRoute(
    val characterId: String,
    //val character: CharacterModel, //TODO: investigate serializable as a field
    val characterJson: String
)

@Serializable
data class CharacterImageRoute(
    val imageUrl: String = ""
)

@Serializable
data class LocationRoute(
    val locationId: String
)

@Serializable
object LocationsRoute

@Serializable
data class EpisodeRoute(
    val episodeId: String
)

@Serializable
object EpisodesRoute

