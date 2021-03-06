package com.example.cleanarchitecturesolution.features.location.all.presentation.model

import com.example.cleanarchitecturesolution.features.location.domain.model.Location

data class LocationDisplayable(
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
) {
    constructor(location: Location) : this(
        location.dimension,
        location.id,
        location.name,
        location.residents,
        location.type,
        location.url
    )
}

data class LocationUiState(
    val isFetchingLocation: Boolean = false,
    val locationItems: List<LocationDisplayable> = listOf(),
    val errorMessage: String? = null,
)

data class LocationDetailsUiState(
    val isFetchingLocation: Boolean = false,
    val location: LocationDisplayable? = null,
    val errorMessage: String? = null,
)
