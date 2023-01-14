package com.example.rickandmorty.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.extensions.getIds
import com.example.rickandmorty.app.network.dto.CharacterDTO
import com.example.rickandmorty.app.network.dto.LocationDTO
import com.example.rickandmorty.app.network.repositories.CharactersRepository
import com.example.rickandmorty.app.network.repositories.LocationsRepository
import com.example.rickandmorty.app.utils.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepo: LocationsRepository,
    private val charactersRepo: CharactersRepository,
) :
    ViewModel() {

    var locationFlow = MutableStateFlow<ViewState<LocationDTO>>(ViewState.Initial)

    var charactersFlow = MutableStateFlow<ViewState<List<CharacterDTO>>>(ViewState.Initial)

    fun getLocation(locationId: Int) {

        viewModelScope.launch {

            locationFlow.emit(ViewState.Loading)

            kotlin.runCatching {
                locationRepo.getLocation(locationId = locationId)

            }.onSuccess {
                locationFlow.emit(ViewState.Populated(it))
                if (it.residents.isNotEmpty()) {
                    getCharacters(it.residents)
                } else {
                    charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                locationFlow.emit(ViewState.Error(errorMessage = "Error loading location"))
            }
        }
    }

    private suspend fun getCharacters(characterUrls: List<String>) {
        charactersFlow.emit(ViewState.Loading)
        kotlin.runCatching {
            charactersRepo.getCharactersByIds(characterUrls.getIds())
        }.onSuccess {
            charactersFlow.emit(ViewState.Populated(it))
        }.onFailure {
            charactersFlow.emit(ViewState.Error(errorMessage = "Error loading characters"))
        }
    }
}
