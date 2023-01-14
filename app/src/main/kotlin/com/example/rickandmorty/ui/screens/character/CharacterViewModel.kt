package com.example.rickandmorty.ui.screens.character

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.extensions.getIds
import com.example.rickandmorty.app.network.dto.CharacterDTO
import com.example.rickandmorty.app.network.dto.EpisodeDTO
import com.example.rickandmorty.app.network.repositories.CharactersRepository
import com.example.rickandmorty.app.network.repositories.EpisodesRepository
import com.example.rickandmorty.app.utils.ViewState
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

    var characterFlow = MutableStateFlow<ViewState<CharacterDTO>>(ViewState.Initial)

    var episodesFlow = MutableStateFlow<ViewState<List<EpisodeDTO>>>(ViewState.Initial)

    fun getCharacter(characterId: Int) {

        viewModelScope.launch {

            characterFlow.emit(ViewState.Loading)

            runCatching {
                charactersRepo.getCharacter(characterId = characterId)
            }.onSuccess {
                characterFlow.emit(ViewState.Populated(it))
                getEpisodes(it.episode)
            }.onFailure {
                characterFlow.emit(ViewState.Error(errorMessage = "Error loading character."))
            }
        }
    }

    fun setCharacter(character: CharacterDTO) {
        viewModelScope.launch {
            characterFlow.emit(ViewState.Populated(character))
            getEpisodes(character.episode)
        }
    }

    private suspend fun getEpisodes(episodeUrls: List<String>) {
        episodesFlow.emit(ViewState.Loading)
        runCatching {
            episodesRepo.getEpisodesByIds(episodeUrls.getIds())
        }.onSuccess {
            episodesFlow.emit(ViewState.Populated(it))
        }.onFailure {
            episodesFlow.emit(ViewState.Error(errorMessage = "Error loading episodes."))
        }
    }
}
