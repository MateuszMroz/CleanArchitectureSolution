package com.example.cleanarchitecturesolution.features.location

import com.example.cleanarchitecturesolution.features.location.domain.model.Location

interface LocationRepository {
    suspend fun fetchLocations(): List<Location>
}
