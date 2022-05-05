package com.example.cleanarchitecturesolution.features.location.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.data.remote.model.LocationResponse
import com.example.cleanarchitecturesolution.core.exception.ErrorWrapper
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.features.location.data.local.LocationDao
import com.example.cleanarchitecturesolution.features.location.data.local.model.LocationCached
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
internal class LocationRepositoryImplTest {

    private var errorWrapper: ErrorWrapper = mockk(relaxed = true)

    @Test
    fun `GIVEN network is connected WHEN locations request THEN fetch locations from API`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getLocations() } returns LocationResponse.mock()
            }
            val locationDao = mockk<LocationDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: LocationRepository = LocationRepositoryImpl(
                api,
                locationDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchLocations()

            coVerify { api.getLocations() }
        }

    @Test
    fun `GIVEN network is connected AND successful fetch WHEN locations request THEN save locations to local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi> {
                coEvery { getLocations() } returns LocationResponse.mock()
            }
            val locationDao = mockk<LocationDao>(relaxed = true)
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns true
            }
            val repository: LocationRepository = LocationRepositoryImpl(
                api,
                locationDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchLocations()

            coVerify { locationDao.saveLocations(*anyVararg()) }
        }

    @Test
    fun `GIVEN network is disconnected WHEN locations request THEN get locations from local storage`() =
        runTest {
            val api = mockk<RickAndMortyApi>(relaxed = true)
            val locationDao = mockk<LocationDao> {
                coEvery { getLocations() } returns listOf(LocationCached.mock(),
                    LocationCached.mock())
            }
            val networkStateProvider = mockk<NetworkStateProvider> {
                coEvery { isNetworkAvailable() } returns false
            }
            val repository: LocationRepository = LocationRepositoryImpl(
                api,
                locationDao,
                networkStateProvider,
                errorWrapper
            )

            repository.fetchLocations()

            coVerify { locationDao.getLocations() }
        }
}
