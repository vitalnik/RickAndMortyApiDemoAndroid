package com.example.rickandmorty.app.data.repositories

import com.example.rickandmorty.app.data.dto.LocationDto
import com.example.rickandmorty.app.data.dto.LocationsDto
import com.example.rickandmorty.app.data.dto.toDomain
import com.example.rickandmorty.app.data.network.NetworkClient
import com.example.rickandmorty.app.domain.models.LocationModel
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(
    private val networkClient: NetworkClient,
) {

    suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationModel> {
        val url = "${NetworkClient.BASE_URL}/location?page=$pageIndex$searchQuery"
        return try {
            val response = networkClient.client.get<LocationsDto> {
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

    suspend fun getLocation(locationId: Int): LocationModel {
        val url = "${NetworkClient.BASE_URL}/location/$locationId"
        val response = networkClient.client.get<LocationDto> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response.toDomain()
    }
}

