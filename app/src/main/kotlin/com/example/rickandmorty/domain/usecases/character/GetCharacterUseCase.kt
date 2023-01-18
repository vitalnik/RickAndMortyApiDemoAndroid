package com.example.rickandmorty.domain.usecases.character

import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend operator fun invoke(characterId: Int): CharacterModel =
        repo.getCharacter(characterId = characterId)

}