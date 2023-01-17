package com.example.rickandmorty.ui.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.data.repositories.CharactersRepository
import com.example.rickandmorty.app.data.repositories.EpisodesRepository
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val charactersRepo: CharactersRepository,
    private val episodesRepo: EpisodesRepository,
) :
    ViewModel() {

    var characterFlow = MutableStateFlow<ViewState<CharacterModel>>(ViewState.Initial)

    var episodesFlow = MutableStateFlow<ViewState<List<EpisodeModel>>>(ViewState.Initial)

    fun getCharacter(characterId: Int) {

        viewModelScope.launch {

            characterFlow.emit(ViewState.Loading)

            runCatching {
                charactersRepo.getCharacter(characterId = characterId)
            }.onSuccess {
                characterFlow.emit(ViewState.Populated(it))
                getEpisodes(it.episodeIds)
            }.onFailure {
                characterFlow.emit(ViewState.Error(errorMessage = "Error loading character."))
            }
        }
    }

    fun setCharacter(character: CharacterModel) {
        viewModelScope.launch {
            characterFlow.emit(ViewState.Populated(character))
            getEpisodes(character.episodeIds)
        }
    }

    private suspend fun getEpisodes(episodeUrls: List<Int>) {
        episodesFlow.emit(ViewState.Loading)
        runCatching {
            episodesRepo.getEpisodesByIds(episodeUrls.idsQuery())
        }.onSuccess {
            episodesFlow.emit(ViewState.Populated(it))
        }.onFailure {
            episodesFlow.emit(ViewState.Error(errorMessage = "Error loading episodes."))
        }
    }
}
