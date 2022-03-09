package com.example.cleanarchitecturesolution.features.location.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.features.location.data.local.LocationDao
import com.example.cleanarchitecturesolution.features.location.data.local.model.LocationCached
import com.example.cleanarchitecturesolution.features.location.domain.model.Location

class LocationRepositoryImpl(
    private val api: RickAndMortyApi,
    private val locationDao: LocationDao,
    private val networkStateProvider: NetworkStateProvider,
) : LocationRepository {

    override suspend fun fetchLocations(): List<Location> {
        return if (networkStateProvider.isNetworkAvailable()) {
            getLocationsFromRemote()
                .also { saveLocationsToLocal(it) }
        } else {
            getLocationsFromLocal()
        }
    }

    private suspend fun getLocationsFromRemote(): List<Location> {
        return api.getLocations()
            .results
            .map { it.toLocation() }
    }

    private fun getLocationsFromLocal(): List<Location> {
        return locationDao.getLocations()
            .map { it.toLocation() }
    }

    private fun saveLocationsToLocal(locations: List<Location>) {
        locations.map { LocationCached(it) }
            .toTypedArray()
            .let { locationDao.saveLocations(*it) }
    }
}
