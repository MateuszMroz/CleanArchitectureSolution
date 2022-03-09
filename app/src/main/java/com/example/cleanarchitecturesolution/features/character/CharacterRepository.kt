package com.example.cleanarchitecturesolution.features.character

import com.example.cleanarchitecturesolution.features.character.domain.model.Character

interface CharacterRepository {
    suspend fun fetchCharacters(): List<Character>
}
