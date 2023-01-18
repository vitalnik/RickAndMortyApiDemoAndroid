package com.example.rickandmorty.domain.repositories

import com.example.rickandmorty.domain.models.CharacterModel

interface CharacterRepository {
    suspend fun getCharacters(pageIndex: Int, searchQuery: String): List<CharacterModel>
    suspend fun getCharactersByIds(characterIds: String): List<CharacterModel>
    suspend fun getCharacter(characterId: Int): CharacterModel
}