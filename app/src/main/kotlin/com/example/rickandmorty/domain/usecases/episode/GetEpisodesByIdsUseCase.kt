package com.example.rickandmorty.domain.usecases.episode

import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.repositories.EpisodeRepository
import javax.inject.Inject

class GetEpisodesByIdsUseCase @Inject constructor(private val repo: EpisodeRepository) {

    suspend operator fun invoke(episodeIds: String): List<EpisodeModel> {
        return repo.getEpisodesByIds(episodeIds = episodeIds)
    }

}