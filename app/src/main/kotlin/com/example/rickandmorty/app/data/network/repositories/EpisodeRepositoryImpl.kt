package com.example.rickandmorty.app.data.network.repositories

import com.example.rickandmorty.app.data.network.NetworkClient
import com.example.rickandmorty.app.data.network.dto.EpisodeDto
import com.example.rickandmorty.app.data.network.dto.EpisodesDto
import com.example.rickandmorty.app.data.network.dto.toDomain
import com.example.rickandmorty.app.domain.models.EpisodeModel
import com.example.rickandmorty.app.domain.repositories.EpisodeRepository
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : EpisodeRepository {

    override suspend fun getEpisodes(pageIndex: Int): List<EpisodeModel> {
        val url = "${NetworkClient.BASE_URL}/episode?page=$pageIndex"
        return try {
            val response = networkClient.client.get<EpisodesDto> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            response.results.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            listOf()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEpisode(episodeId: Int): EpisodeModel {
        val url = "${NetworkClient.BASE_URL}/episode/$episodeId"
        val response = networkClient.client.get<EpisodeDto> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response.toDomain()
    }

    override suspend fun getEpisodesByIds(episodeIds: String): List<EpisodeModel> {
        val url = "${NetworkClient.BASE_URL}/episode/$episodeIds"
        return if (episodeIds.contains(",")) {
            networkClient.client.get<List<EpisodeDto>> {
                url(url)
                contentType(ContentType.Application.Json)
            }.map {
                it.toDomain()
            }
        } else {
            val singleDTO = networkClient.client.get<EpisodeDto> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            listOf(singleDTO.toDomain())
        }
    }
}

