package com.example.rickandmorty.domain.repositories

import com.example.rickandmorty.domain.models.LocationModel

interface LocationRepository {
    suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationModel>
    suspend fun getLocation(id: Int): LocationModel
}