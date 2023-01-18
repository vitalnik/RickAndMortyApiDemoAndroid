package com.example.rickandmorty.app.domain.usecases.episode

import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.repositories.EpisodeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetEpisodesByIdsUseCase @Inject constructor(private val repo: EpisodeRepository) {

    suspend operator fun invoke(episodeIds: String): List<EpisodeModel> {
        return repo.getEpisodesByIds(episodeIds = episodeIds)
    }

}