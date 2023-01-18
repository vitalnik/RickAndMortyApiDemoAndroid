package com.example.rickandmorty.app.data.network.repositories

import com.example.rickandmorty.app.data.network.NetworkClient
import com.example.rickandmorty.app.data.network.dto.CharacterDto
import com.example.rickandmorty.app.data.network.dto.CharactersDto
import com.example.rickandmorty.app.data.network.dto.toDomain
import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.repositories.CharacterRepository
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : CharacterRepository {

    override suspend fun getCharacters(pageIndex: Int, searchQuery: String): List<CharacterModel> {
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

    override suspend fun getCharacter(characterId: Int): CharacterModel {
        val url = "${NetworkClient.BASE_URL}/character/$characterId"
        val response = networkClient.client.get<CharacterDto> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response.toDomain()
    }

    override suspend fun getCharactersByIds(characterIds: String): List<CharacterModel> {
        val url = "${NetworkClient.BASE_URL}/character/$characterIds"
        return if (characterIds.contains(",")) {
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


