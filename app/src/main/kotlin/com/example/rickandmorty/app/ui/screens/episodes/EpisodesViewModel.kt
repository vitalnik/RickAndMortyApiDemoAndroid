package com.example.rickandmorty.app.ui.screens.episodes

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class TabItem(val label: String, val id: Int)

@HiltViewModel
class EpisodesViewModel @Inject constructor() :
    ViewModel() {

    val selectedSeason = mutableStateOf(0)

    companion object {
        const val seasonCount = 6
    }


}

