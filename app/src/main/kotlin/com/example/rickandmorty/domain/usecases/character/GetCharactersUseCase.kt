package com.example.rickandmorty.domain.usecases.character

import com.example.rickandmorty.domain.models.CharacterModel
import com.example.rickandmorty.domain.repositories.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(private val repo: CharacterRepository) {

    suspend operator fun invoke(pageIndex: Int, searchQuery: String): List<CharacterModel> {
        return repo.getCharacters(pageIndex = pageIndex, searchQuery = searchQuery)
    }

}
