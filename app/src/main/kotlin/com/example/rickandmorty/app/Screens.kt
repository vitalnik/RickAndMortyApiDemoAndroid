package com.example.rickandmorty.app

sealed class Screen(val route: String) {
    object Home : Screen("home")

    object Characters : Screen("characters")
    object Character : Screen("character?characterJson={characterJson}&characterId={characterId}") {
        fun createRoute(characterId: String, characterJson: String) =
            "character?characterId=$characterId&characterJson=$characterJson"
    }

    object Locations : Screen("locations")
    object Location : Screen("location/{locationId}") {
        fun createRoute(locationId: String) = "location/$locationId"
    }

    object Episodes : Screen("episodes")
    object Episode : Screen("episode/{episodeId}") {
        fun createRoute(episodeId: String) = "episode/$episodeId"
    }
}