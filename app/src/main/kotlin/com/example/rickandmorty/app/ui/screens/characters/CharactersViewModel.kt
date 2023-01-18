package com.example.rickandmorty.app.ui.screens.characters

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor() :
    ViewModel() {

    private val _searchPanelVisible = mutableStateOf(false)
    val searchPanelVisible: State<Boolean> = _searchPanelVisible

    fun showSearchPanel() {
        _searchPanelVisible.value = true
    }

    fun hideSearchPanel() {
        _searchPanelVisible.value = false
    }

}

