package com.example.rickandmorty.app.network.repositories

import android.util.Log
import com.example.rickandmorty.app.network.NetworkClient
import com.example.rickandmorty.app.network.dto.EpisodeDTO
import com.example.rickandmorty.app.network.dto.EpisodesDTO
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EpisodesRepository @Inject constructor(
    private val networkClient: NetworkClient,
) {

    suspend fun getEpisodes(pageIndex: Int): List<EpisodeDTO> {
        val url = "${NetworkClient.BASE_URL}/episode?page=$pageIndex"
        Log.d("TAG", ">>> $url")
        return try {
            val response = networkClient.client.get<EpisodesDTO> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            response.results
        } catch (e: ClientRequestException) {
            listOf()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getEpisode(episodeId: Int): EpisodeDTO {
        val url = "${NetworkClient.BASE_URL}/episode/$episodeId"
        Log.d("TAG", ">>> $url")
        val response = networkClient.client.get<EpisodeDTO> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun getEpisodesByIds(episodesIds: String): List<EpisodeDTO> {
        val url = "${NetworkClient.BASE_URL}/episode/$episodesIds"
        Log.d("TAG", ">>> $url")
        return if (episodesIds.contains(",")) {
            networkClient.client.get<List<EpisodeDTO>> {
                url(url)
                contentType(ContentType.Application.Json)
            }
        } else {
            val singleDTO = networkClient.client.get<EpisodeDTO> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            listOf(singleDTO)
        }
    }
}

