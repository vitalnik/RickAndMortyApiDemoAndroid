package com.example.rickandmorty.domain.usecases.location

import com.example.rickandmorty.domain.models.LocationModel
import com.example.rickandmorty.domain.repositories.LocationRepository
import javax.inject.Inject

class GetLocationUseCase @Inject constructor(private val repo: LocationRepository) {

    suspend operator fun invoke(id: Int): LocationModel =
        repo.getLocation(id = id)

}