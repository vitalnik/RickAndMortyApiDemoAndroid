package com.example.rickandmorty.data.network.repositories

import com.example.rickandmorty.data.network.NetworkClient
import com.example.rickandmorty.data.network.dto.LocationDto
import com.example.rickandmorty.data.network.dto.LocationsDto
import com.example.rickandmorty.data.network.dto.toDomain
import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.repositories.LocationRepository
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : LocationRepository {

    override suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationModel> {
        val url = "${NetworkClient.BASE_URL}/location?page=$pageIndex$searchQuery"
        return try {
            val response = networkClient.client.get(url) {
                contentType(ContentType.Application.Json)
            }
            response.body<LocationsDto>().results.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            emptyList()
        }
    }

    override suspend fun getLocation(id: Int): LocationModel {
        val url = "${NetworkClient.BASE_URL}/location/$id"
        val response = networkClient.client.get(url) {
            contentType(ContentType.Application.Json)
        }
        return response.body<LocationDto>().toDomain()
    }
}

