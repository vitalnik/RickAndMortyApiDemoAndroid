package com.example.rickandmorty.ui.screens.episode

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
class EpisodeViewModel @Inject constructor(
    private val episodeRepo: EpisodesRepository,
    private val charactersRepo: CharactersRepository,
) :
    ViewModel() {

    var episodeFlow = MutableStateFlow<ViewState<EpisodeDTO>>(ViewState.Initial)

    var charactersFlow = MutableStateFlow<ViewState<List<CharacterDTO>>>(ViewState.Initial)

    fun getEpisode(episodeId: Int) {

        viewModelScope.launch {

            episodeFlow.emit(ViewState.Loading)

            kotlin.runCatching {
                episodeRepo.getEpisode(episodeId = episodeId)
            }.onSuccess {
                episodeFlow.emit(ViewState.Populated(it))
                if (it.characters.isNotEmpty()) {
                    getCharacters(it.characters)
                } else {
                    charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                episodeFlow.emit(ViewState.Error(errorMessage = "Error loading episode"))
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
