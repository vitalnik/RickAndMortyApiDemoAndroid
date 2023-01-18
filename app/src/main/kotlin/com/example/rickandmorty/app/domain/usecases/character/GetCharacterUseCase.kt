package com.example.rickandmorty.app.domain.usecases.character

import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.repositories.CharacterRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetCharacterUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend operator fun invoke(characterId: Int): CharacterModel =
        repo.getCharacter(characterId = characterId)

}