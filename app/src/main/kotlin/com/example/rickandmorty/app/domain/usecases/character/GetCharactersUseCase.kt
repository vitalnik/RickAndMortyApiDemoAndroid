package com.example.rickandmorty.app.domain.usecases.character

import com.example.rickandmorty.app.domain.models.CharacterModel
import com.example.rickandmorty.app.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend operator fun invoke(pageIndex: Int, searchQuery: String): List<CharacterModel> {
        return repo.getCharacters(pageIndex = pageIndex, searchQuery = searchQuery)
    }

}