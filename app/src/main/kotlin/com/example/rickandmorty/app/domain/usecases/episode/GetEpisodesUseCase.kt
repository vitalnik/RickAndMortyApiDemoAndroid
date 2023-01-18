package com.example.rickandmorty.app.domain.usecases.episode

import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.repositories.EpisodeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEpisodesUseCase @Inject constructor(private val repo: EpisodeRepository) {

    suspend operator fun invoke(pageIndex: Int): List<EpisodeModel> {
        return repo.getEpisodes(pageIndex = pageIndex)
    }

}