package com.example.rickandmorty.app.ui.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.usecases.character.GetCharactersByIdsUseCase
import com.example.rickandmorty.domain.usecases.episode.GetEpisodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val getCharactersByIdsUseCase: GetCharactersByIdsUseCase,
) :
    ViewModel() {

    private var _episodeFlow = MutableStateFlow<ViewState<EpisodeModel>>(ViewState.Initial)
    var episodeFlow: StateFlow<ViewState<EpisodeModel>> = _episodeFlow

    private var _charactersFlow =
        MutableStateFlow<ViewState<List<CharacterModel>>>(ViewState.Initial)
    var charactersFlow: StateFlow<ViewState<List<CharacterModel>>> = _charactersFlow

    fun getEpisode(episodeId: Int, isPullRefresh: Boolean = false) {
        viewModelScope.launch {
            _episodeFlow.emit(ViewState.Loading)
            kotlin.runCatching {
                if (isPullRefresh) {
                    delay(2000)
                }
                getEpisodeUseCase(episodeId = episodeId)
            }.onSuccess {
                _episodeFlow.emit(ViewState.Populated(it))
                if (it.characterIds.isNotEmpty()) {
                    getCharacters(it.characterIds.idsQuery())
                } else {
                    _charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                _episodeFlow.emit(ViewState.Error(errorMessage = "Error loading episode"))
            }
        }
    }

    private suspend fun getCharacters(idsQuery: String) {
        _charactersFlow.emit(ViewState.Loading)
        kotlin.runCatching {
            getCharactersByIdsUseCase(idsQuery)
        }.onSuccess {
            _charactersFlow.emit(ViewState.Populated(it))
        }.onFailure {
            _charactersFlow.emit(ViewState.Error(errorMessage = "Error loading characters"))
        }
    }
}
