package com.example.rickandmorty.ui.screens.characters

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() :
    ViewModel() {

    val searchPanelVisible = mutableStateOf(false)

    fun showSearchPanel() {
        searchPanelVisible.value = true
    }

    fun hideSearchPanel() {
        searchPanelVisible.value = false
    }

}

