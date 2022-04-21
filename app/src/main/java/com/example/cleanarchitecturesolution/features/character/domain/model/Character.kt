package com.example.cleanarchitecturesolution.features.character.domain.model

data class Character(
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val lastLocation: LocationCharacter,
    val name: String,
    val originLocation: LocationCharacter,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
) {
    companion object
}

data class LocationCharacter(
    val name: String,
    val url: String,
) {
    companion object
}
