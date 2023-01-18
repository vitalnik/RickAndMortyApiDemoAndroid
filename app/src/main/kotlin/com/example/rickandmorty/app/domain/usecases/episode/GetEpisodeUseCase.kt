package com.example.rickandmorty.app.domain.usecases.episode

import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.repositories.EpisodeRepository
import javax.inject.Inject

class GetEpisodeUseCase @Inject constructor(private val repo: EpisodeRepository) {

    suspend operator fun invoke(episodeId: Int): EpisodeModel =
        repo.getEpisode(episodeId = episodeId)

}