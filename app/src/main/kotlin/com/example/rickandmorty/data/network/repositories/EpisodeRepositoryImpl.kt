package com.example.rickandmorty.data.network.repositories

import com.example.rickandmorty.data.network.NetworkClient
import com.example.rickandmorty.data.network.dto.EpisodeDto
import com.example.rickandmorty.data.network.dto.EpisodesDto
import com.example.rickandmorty.data.network.dto.toDomain
import com.example.rickandmorty.data.network.exceptions.NetworkException
import com.example.rickandmorty.domain.models.EpisodeModel
import com.example.rickandmorty.domain.repositories.EpisodeRepository
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import timber.log.Timber
import javax.inject.Inject

class EpisodeRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : EpisodeRepository {

    override suspend fun getEpisodes(pageIndex: Int, searchQuery: String): List<EpisodeModel> {
        val url = "${NetworkClient.BASE_URL}/episode?page=$pageIndex$searchQuery"
        return try {
            val response = networkClient.client.get(url) {
                contentType(ContentType.Application.Json)
            }
            response.body<EpisodesDto>().results.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            listOf()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getEpisode(episodeId: Int): EpisodeModel {
        return try {
            val url = "${NetworkClient.BASE_URL}/episode/$episodeId"
            val response: HttpResponse = networkClient.client.get(url) {
                contentType(ContentType.Application.Json)
            }

            if (response.status.isSuccess()) {
                response.body<EpisodeDto>().toDomain()
            } else {
                throw NetworkException(response.status.value, response.status.description)
            }
        } catch (e: Throwable) {
            Timber.d(">>> getEpisode exception: $e")
            when (e) {
                is NetworkException -> throw e
                else -> throw NetworkException(999, e.message ?: "Unknown error")
            }
        }
    }

    override suspend fun getEpisodesByIds(episodeIds: String): List<EpisodeModel> {
        val url = "${NetworkClient.BASE_URL}/episode/$episodeIds"
        return if (episodeIds.contains(",")) {
            networkClient.client.get(url) {
                contentType(ContentType.Application.Json)
            }.body<List<EpisodeDto>>().map {
                it.toDomain()
            }
        } else {
            val singleDTO = networkClient.client.get(url) {
                contentType(ContentType.Application.Json)
            }
            listOf(singleDTO.body<EpisodeDto>().toDomain())
        }
    }
}

