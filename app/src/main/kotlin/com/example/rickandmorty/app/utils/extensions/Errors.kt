package com.example.rickandmorty.app.utils.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.rickandmorty.R

@Composable
fun String.toErrorMessage(): String =
    when (this) {
        "error_loading_characters" -> stringResource(id = R.string.error_loading_characters)
        "error_loading_character" -> stringResource(id = R.string.error_loading_character)
        "error_loading_locations" -> stringResource(id = R.string.error_loading_locations)
        "error_loading_location" -> stringResource(id = R.string.error_loading_location)
        "error_loading_episodes" -> stringResource(id = R.string.error_loading_episodes)
        "error_loading_episode" -> stringResource(id = R.string.error_loading_episode)
        else -> stringResource(id = R.string.unknown_error)
    }
