package com.example.rickandmorty.data.network.repositories

import com.example.rickandmorty.data.network.NetworkClient
import com.example.rickandmorty.data.network.dto.LocationDto
import com.example.rickandmorty.data.network.dto.LocationsDto
import com.example.rickandmorty.data.network.dto.toDomain
import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.repositories.LocationRepository
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val networkClient: NetworkClient,
) : LocationRepository {

    override suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationModel> {
        val url = "${NetworkClient.BASE_URL}/location?page=$pageIndex$searchQuery"
        return try {
            val response = networkClient.client.get<LocationsDto>(url) {
                contentType(ContentType.Application.Json)
            }
            response.results.map { it.toDomain() }
        } catch (e: ClientRequestException) {
            listOf()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getLocation(id: Int): LocationModel {
        val url = "${NetworkClient.BASE_URL}/location/$id"
        val response = networkClient.client.get<LocationDto>(url) {
            contentType(ContentType.Application.Json)
        }
        return response.toDomain()
    }
}

