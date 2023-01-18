package com.example.rickandmorty.ui.screens.episode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.usecases.character.GetCharactersByIdsUseCase
import com.example.rickandmorty.app.domain.usecases.episode.GetEpisodeUseCase
import com.example.rickandmorty.app.utils.ViewState
import com.example.rickandmorty.app.utils.extensions.idsQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeViewModel @Inject constructor(
    private val getEpisodeUseCase: GetEpisodeUseCase,
    private val getCharactersByIdsUseCase: GetCharactersByIdsUseCase,
) :
    ViewModel() {

    var episodeFlow = MutableStateFlow<ViewState<EpisodeModel>>(ViewState.Initial)

    var charactersFlow = MutableStateFlow<ViewState<List<CharacterModel>>>(ViewState.Initial)

    fun getEpisode(episodeId: Int, isPullRefresh: Boolean = false) {
        viewModelScope.launch {
            episodeFlow.emit(ViewState.Loading)
            kotlin.runCatching {
                if (isPullRefresh) {
                    delay(2000)
                }
                getEpisodeUseCase(episodeId = episodeId)
            }.onSuccess {
                episodeFlow.emit(ViewState.Populated(it))
                if (it.characterIds.isNotEmpty()) {
                    getCharacters(it.characterIds.idsQuery())
                } else {
                    charactersFlow.emit(ViewState.Populated(listOf()))
                }
            }.onFailure {
                episodeFlow.emit(ViewState.Error(errorMessage = "Error loading episode"))
            }
        }
    }

    private suspend fun getCharacters(idsQuery: String) {
        charactersFlow.emit(ViewState.Loading)
        kotlin.runCatching {
            getCharactersByIdsUseCase(idsQuery)
        }.onSuccess {
            charactersFlow.emit(ViewState.Populated(it))
        }.onFailure {
            charactersFlow.emit(ViewState.Error(errorMessage = "Error loading characters"))
        }
    }
}
