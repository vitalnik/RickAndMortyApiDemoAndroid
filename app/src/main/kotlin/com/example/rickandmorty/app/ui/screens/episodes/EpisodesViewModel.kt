package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class TabItem(val label: String, val id: Int)

@HiltViewModel
class EpisodesViewModel @Inject constructor() :
    ViewModel() {

    private val seasonCount = 5

    val selectedSeason = mutableStateOf(0)

    val seasons = mutableListOf<TabItem>()

    init {
        seasons.add(TabItem("All Seasons", 0))
        repeat(seasonCount) {
            seasons.add(TabItem("Season ${it + 1}", it + 1))
        }
    }

}

