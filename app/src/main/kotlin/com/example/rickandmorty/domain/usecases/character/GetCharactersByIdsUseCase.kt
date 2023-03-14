package com.example.rickandmorty.domain.usecases.character

import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharactersByIdsUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend operator fun invoke(characterIds: String): List<CharacterModel> {
        return repo.getCharactersByIds(characterIds = characterIds)
    }

}
