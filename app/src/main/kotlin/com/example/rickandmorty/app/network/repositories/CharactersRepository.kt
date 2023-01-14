package com.example.rickandmorty.app.network.repositories

import android.util.Log
import com.example.rickandmorty.app.network.NetworkClient
import com.example.rickandmorty.app.network.dto.CharacterDTO
import com.example.rickandmorty.app.network.dto.CharactersDTO
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(
    private val networkClient: NetworkClient,
) {

    suspend fun getCharacters(pageIndex: Int, searchQuery: String): List<CharacterDTO> {
        val url = "${NetworkClient.BASE_URL}/character?page=$pageIndex$searchQuery"
        Log.d("TAG", ">>> $url")
        return try {
            val response = networkClient.client.get<CharactersDTO> {
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

    suspend fun getCharacter(characterId: Int): CharacterDTO {
        val url = "${NetworkClient.BASE_URL}/character/$characterId"
        Log.d("TAG", ">>> $url")
        val response = networkClient.client.get<CharacterDTO> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response
    }

    suspend fun getCharactersByIds(characterIds: String): List<CharacterDTO> {
        val url = "${NetworkClient.BASE_URL}/character/$characterIds"
        Log.d("TAG", ">>> $url")
        return if (characterIds.contains(",")) {
            networkClient.client.get<List<CharacterDTO>> {
                url(url)
                contentType(ContentType.Application.Json)
            }
        } else {
            val singleDTO = networkClient.client.get<CharacterDTO> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            listOf(singleDTO)
        }
    }
}


