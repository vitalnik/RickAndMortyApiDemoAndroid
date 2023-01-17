package com.example.rickandmorty.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.data.repositories.CharactersRepository
import com.example.rickandmorty.app.data.repositories.LocationsRepository
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.LocationModel
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
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

    var locationFlow = MutableStateFlow<ViewState<LocationModel>>(ViewState.Initial)

    var charactersFlow = MutableStateFlow<ViewState<List<CharacterModel>>>(ViewState.Initial)

    fun getLocation(locationId: Int) {

        viewModelScope.launch {

            locationFlow.emit(ViewState.Loading)

            kotlin.runCatching {
                locationRepo.getLocation(locationId = locationId)

            }.onSuccess {
                locationFlow.emit(ViewState.Populated(it))
                if (it.residentIds.isNotEmpty()) {
                    getCharacters(it.residentIds)
                } else {
                    charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                locationFlow.emit(ViewState.Error(errorMessage = "Error loading location"))
            }
        }
    }

    private suspend fun getCharacters(characterIds: List<Int>) {
        charactersFlow.emit(ViewState.Loading)
        kotlin.runCatching {
            charactersRepo.getCharactersByIds(characterIds.idsQuery())
        }.onSuccess {
            charactersFlow.emit(ViewState.Populated(it))
        }.onFailure {
            charactersFlow.emit(ViewState.Error(errorMessage = "Error loading characters"))
        }
    }
}
