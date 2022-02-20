package com.example.cleanarchitecturesolution.features.location.domain

import com.example.cleanarchitecturesolution.core.base.UseCase
import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.features.location.domain.model.Location

class GetLocationsUseCase(
    private val repository: LocationRepository
) : UseCase<List<Location>, Unit>() {

    override suspend fun doAction(params: Unit): List<Location> = repository.fetchLocations()
}
