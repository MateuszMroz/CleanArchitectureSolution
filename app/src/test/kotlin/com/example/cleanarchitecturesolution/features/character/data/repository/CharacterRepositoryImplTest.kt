package com.example.cleanarchitecturesolution.features.character.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.data.remote.model.CharacterResponse
import com.example.cleanarchitecturesolution.core.exception.ErrorWrapper
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.character.CharacterRepository
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterDao
import com.example.cleanarchitecturesolution.mock.mock
import com.example.cleanarchitecturesolution.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
internal class CharacterRepositoryImplTest {

    private var errorWrapper: ErrorWrapper = mockk(relaxed = true)

    @Test
    fun `GIVEN network is connected WHEN characters request THEN fetch characters from API`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getCharacters() } returns CharacterResponse.mock()
            }
            val characterDao = mockk<CharacterDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: CharacterRepository = CharacterRepositoryImpl(
                api,
                characterDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchCharacters()

            coVerify { api.getCharacters() }
        }

    @Test
    fun `GIVEN network is connected AND successful fetch WHEN characters request THEN save characters to local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getCharacters() } returns CharacterResponse.mock()
            }
            val characterDao = mockk<CharacterDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: CharacterRepository = CharacterRepositoryImpl(
                api,
                characterDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchCharacters()

            coVerify { characterDao.saveCharacters(*anyVararg()) }
        }

    @Test
    fun `GIVEN network is disconnected WHEN characters request THEN get characters from local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi>(relaxed = true)
            val characterDao = mockk<CharacterDao> {
                coEvery { getCharacters() } returns listOf(
                    CharacterCached.mock(),
                    CharacterCached.mock()
                )
            }
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns false
            }
            val repository: CharacterRepository = CharacterRepositoryImpl(
                api,
                characterDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchCharacters()

            coVerify { characterDao.getCharacters() }
        }
}
