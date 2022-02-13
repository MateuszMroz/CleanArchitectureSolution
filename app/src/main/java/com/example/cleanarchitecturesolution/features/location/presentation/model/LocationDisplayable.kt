package com.example.cleanarchitecturesolution.features.location.presentation.model

import com.example.cleanarchitecturesolution.features.location.domain.model.Location

data class LocationDisplayable(
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
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