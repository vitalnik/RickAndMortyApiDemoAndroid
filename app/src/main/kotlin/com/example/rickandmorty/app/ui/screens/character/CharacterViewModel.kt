package com.example.rickandmorty.app.ui.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.usecases.character.GetCharacterUseCase
import com.example.rickandmorty.domain.usecases.episode.GetEpisodesByIdsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val getEpisodesByIdsUseCase: GetEpisodesByIdsUseCase,
) :
    ViewModel() {

    private var _characterFlow = MutableStateFlow<ViewState<CharacterModel>>(ViewState.Initial)
    var characterFlow: StateFlow<ViewState<CharacterModel>> = _characterFlow

    private var _episodesFlow = MutableStateFlow<ViewState<List<EpisodeModel>>>(ViewState.Initial)
    var episodesFlow: StateFlow<ViewState<List<EpisodeModel>>> = _episodesFlow

    fun getCharacter(characterId: Int) {

        viewModelScope.launch {

            _characterFlow.emit(ViewState.Loading)

            runCatching {
                getCharacterUseCase(characterId = characterId)
            }.onSuccess {
                _characterFlow.emit(ViewState.Populated(it))
                getEpisodes(it.episodeIds)
            }.onFailure {
                _characterFlow.emit(ViewState.Error(errorMessage = "Error loading character."))
            }
        }
    }

    fun setCharacter(character: CharacterModel) {
        viewModelScope.launch {
            _characterFlow.emit(ViewState.Populated(character))
            getEpisodes(character.episodeIds)
        }
    }

    private suspend fun getEpisodes(episodeUrls: List<Int>) {
        _episodesFlow.emit(ViewState.Loading)
        runCatching {
            getEpisodesByIdsUseCase(episodeUrls.idsQuery())
        }.onSuccess {
            _episodesFlow.emit(ViewState.Populated(it))
        }.onFailure {
            _episodesFlow.emit(ViewState.Error(errorMessage = "Error loading episodes."))
        }
    }
}
