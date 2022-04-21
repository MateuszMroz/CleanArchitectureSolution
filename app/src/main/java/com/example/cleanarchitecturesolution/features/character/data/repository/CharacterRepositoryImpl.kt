package com.example.cleanarchitecturesolution.features.character.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.character.CharacterRepository
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterDao
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached
import com.example.cleanarchitecturesolution.features.character.domain.model.Character

class CharacterRepositoryImpl(
    private val api: RickAndMortyApi,
    private val characterDao: CharacterDao,
    private val networkStateProvider: NetworkStateProvider,
) : CharacterRepository {

    override suspend fun fetchCharacters(): List<Character> {
        return if (networkStateProvider.isNetworkAvailable()) {
            getCharactersFromRemote()
                .also { saveCharactersToLocal(it) }
        } else {
            getCharacterFromLocal()
        }
    }

    private suspend fun getCharactersFromRemote(): List<Character> {
        return api.getCharacters()
            .results
            .map { it.toCharacter() }
    }

    private suspend fun getCharacterFromLocal(): List<Character> {
        return characterDao.getCharacters()
            .map { it.toCharacter() }
    }

    private suspend fun saveCharactersToLocal(characters: List<Character>) {
        characters.map { CharacterCached(it) }
            .toTypedArray()
            .let { characterDao.saveCharacters(*it) }
    }
}
