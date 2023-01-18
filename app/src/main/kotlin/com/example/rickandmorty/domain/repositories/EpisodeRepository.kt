package com.example.rickandmorty.domain.repositories

import com.example.rickandmorty.domain.models.EpisodeModel

interface EpisodeRepository {
    suspend fun getEpisodes(pageIndex: Int): List<EpisodeModel>
    suspend fun getEpisodesByIds(episodeIds: String): List<EpisodeModel>
    suspend fun getEpisode(episodeId: Int): EpisodeModel
}