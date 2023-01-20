package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor() :
    ViewModel() {

    val seasons = mutableListOf<String>()

    init {
        seasons.add("All Seasons")
        repeat(5) {
            seasons.add("Season ${it + 1}")
        }
    }

    val selectedSeason = mutableStateOf(seasons[0])

}

