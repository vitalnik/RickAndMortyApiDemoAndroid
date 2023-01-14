package com.example.rickandmorty.app.network.repositories

import android.util.Log
import com.example.rickandmorty.app.network.NetworkClient
import com.example.rickandmorty.app.network.dto.LocationDTO
import com.example.rickandmorty.app.network.dto.LocationsDTO
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationsRepository @Inject constructor(
    private val networkClient: NetworkClient,
) {

    suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationDTO> {
        val url = "${NetworkClient.BASE_URL}/location?page=$pageIndex$searchQuery"
        Log.d("TAG", ">>> $url")
        return try {
            val response = networkClient.client.get<LocationsDTO> {
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

    suspend fun getLocation(locationId: Int): LocationDTO {
        val url = "${NetworkClient.BASE_URL}/location/$locationId"
        Log.d("TAG", ">>> $url")
        val response = networkClient.client.get<LocationDTO> {
            url(url)
            contentType(ContentType.Application.Json)
        }
        return response
    }
}

