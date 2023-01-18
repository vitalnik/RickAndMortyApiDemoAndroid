package com.example.rickandmorty.domain.usecases.location

import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.repositories.LocationRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(private val repo: LocationRepository) {

    suspend operator fun invoke(pageIndex: Int, searchQuery: String): List<LocationModel> {
        return repo.getLocations(pageIndex = pageIndex, searchQuery)
    }

}