package com.example.cleanarchitecturesolution.features.character

import com.example.cleanarchitecturesolution.features.character.domain.model.Character

interface CharacterRepository {
    fun fetchCharacters(): List<Character>
}
