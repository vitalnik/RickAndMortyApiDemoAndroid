package com.example.rickandmorty.app.domain.repositories

import com.example.rickandmorty.app.domain.models.LocationModel

interface LocationRepository {
    suspend fun getLocations(pageIndex: Int, searchQuery: String): List<LocationModel>
    suspend fun getLocation(id: Int): LocationModel
}