package com.example.cleanarchitecturesolution.features.episodes.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeResponse
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.episodes.EpisodesRepository
import com.example.cleanarchitecturesolution.features.episodes.data.local.EpisodesDao
import com.example.cleanarchitecturesolution.features.episodes.data.local.model.EpisodeCached
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
internal class EpisodesRepositoryTest {

    @Test
    fun `GIVEN network is connected WHEN episodes request THEN fetch episodes from API`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getEpisodes() } returns EpisodeResponse.mock()
            }
            val episodesDao = mockk<EpisodesDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: EpisodesRepository = EpisodesRepositoryImpl(
                api,
                episodesDao,
                networkStateProvider
            )

            repository.fetchEpisodes()

            coVerify { api.getEpisodes() }
        }

    @Test
    fun `GIVEN network is connected AND successful fetch WHEN episodes request THEN save episodes to local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getEpisodes() } returns EpisodeResponse.mock()
            }
            val episodesDao = mockk<EpisodesDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: EpisodesRepository = EpisodesRepositoryImpl(
                api,
                episodesDao,
                networkStateProvider
            )

            repository.fetchEpisodes()

            coVerify { episodesDao.saveEpisodes(*anyVararg()) }
        }

    @Test
    fun `GIVEN network is disconnected WHEN episodes request THEN get episodes from local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi>(relaxed = true)
            val episodesDao = mockk<EpisodesDao> {
                coEvery { getEpisodes() } returns listOf(EpisodeCached.mock(), EpisodeCached.mock())
            }
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns false
            }
            val repository: EpisodesRepository = EpisodesRepositoryImpl(
                api,
                episodesDao,
                networkStateProvider
            )

            repository.fetchEpisodes()

            coVerify { episodesDao.getEpisodes() }
        }
}
