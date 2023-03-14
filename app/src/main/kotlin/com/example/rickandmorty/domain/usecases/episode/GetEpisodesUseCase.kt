package com.example.rickandmorty.domain.usecases.episode

import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.repositories.EpisodeRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(private val repo: EpisodeRepository) {

    suspend operator fun invoke(pageIndex: Int, searchQuery: String): List<EpisodeModel> {
        return repo.getEpisodes(pageIndex = pageIndex, searchQuery = searchQuery)
    }

}
