package com.example.rickandmorty.app.data.repositories

import com.example.rickandmorty.app.data.dto.CharacterDto
import com.example.rickandmorty.app.data.dto.CharactersDto
import com.example.rickandmorty.app.data.dto.toDomain
import com.example.rickandmorty.app.data.network.NetworkClient
import com.example.rickandmorty.app.domain.models.CharacterModel
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersRepository @Inject constructor(
    private val networkClient: NetworkClient,
) {

    suspend fun getCharacters(pageIndex: Int, searchQuery: String): List<CharacterModel> {
        val url = "${NetworkClient.BASE_URL}/character?page=$pageIndex$searchQuery"
        return try {
            val response = networkClient.client.get<CharactersDto> {
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

    suspend fun getCharacter(characterId: Int): CharacterModel {
        val url = "${NetworkClient.BASE_URL}/character/$characterId"
        val response = networkClient.client.get<CharacterDto> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response.toDomain()
    }

    suspend fun getCharactersByIds(idsQuery: String): List<CharacterModel> {
        val url = "${NetworkClient.BASE_URL}/character/$idsQuery"
        return if (idsQuery.contains(",")) {
            networkClient.client.get<List<CharacterDto>> {
                url(url)
                contentType(ContentType.Application.Json)
            }.map { it.toDomain() }
        } else {
            val singleDTO = networkClient.client.get<CharacterDto> {
                url(url)
                contentType(ContentType.Application.Json)
            }
            listOf(singleDTO.toDomain())
        }
    }
}


