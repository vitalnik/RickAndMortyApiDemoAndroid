package com.example.rickandmorty.app.ui.screens.location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.usecases.character.GetCharactersByIdsUseCase
import com.example.rickandmorty.domain.usecases.location.GetLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLocationUseCase: GetLocationUseCase,
    private val getCharactersByIdsUseCase: GetCharactersByIdsUseCase,
) :
    ViewModel() {

    private var _locationFlow = MutableStateFlow<ViewState<LocationModel>>(ViewState.Initial)
    var locationFlow: StateFlow<ViewState<LocationModel>> = _locationFlow

    private var _charactersFlow =
        MutableStateFlow<ViewState<List<CharacterModel>>>(ViewState.Initial)
    var charactersFlow: StateFlow<ViewState<List<CharacterModel>>> = _charactersFlow

    fun getLocation(locationId: Int) {

        viewModelScope.launch {

            _locationFlow.emit(ViewState.Loading)

            kotlin.runCatching {
                getLocationUseCase(id = locationId)

            }.onSuccess {
                _locationFlow.emit(ViewState.Populated(it))
                if (it.residentIds.isNotEmpty()) {
                    getCharacters(it.residentIds)
                } else {
                    _charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                _locationFlow.emit(ViewState.Error(errorCode = "error_loading_location"))
            }
        }
    }

    private suspend fun getCharacters(characterIds: List<Int>) {
        _charactersFlow.emit(ViewState.Loading)
        kotlin.runCatching {
            getCharactersByIdsUseCase(characterIds.idsQuery())
        }.onSuccess {
            _charactersFlow.emit(ViewState.Populated(it))
        }.onFailure {
            _charactersFlow.emit(ViewState.Error(errorCode = "error_loading_characters"))
        }
    }
}
