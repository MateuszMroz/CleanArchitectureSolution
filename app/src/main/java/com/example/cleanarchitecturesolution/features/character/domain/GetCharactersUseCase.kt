package com.example.cleanarchitecturesolution.features.character.domain

import com.example.cleanarchitecturesolution.core.base.UseCase
import com.example.cleanarchitecturesolution.features.character.CharacterRepository
import com.example.cleanarchitecturesolution.features.character.domain.model.Character

class GetCharactersUseCase(
    private val repository: CharacterRepository
) : UseCase<List<Character>, Unit>() {

    override suspend fun doAction(params: Unit): List<Character> = repository.fetchCharacters()
}
