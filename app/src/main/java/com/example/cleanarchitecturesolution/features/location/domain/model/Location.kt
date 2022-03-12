package com.example.cleanarchitecturesolution.features.location.domain.model

data class Location(
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
)
