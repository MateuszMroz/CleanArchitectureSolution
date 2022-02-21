package com.example.cleanarchitecturesolution.features.location

import com.example.cleanarchitecturesolution.features.location.domain.model.Location

interface LocationRepository {
    fun fetchLocations(): List<Location>
}
