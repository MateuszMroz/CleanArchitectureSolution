package com.example.cleanarchitecturesolution.features.character.all.presentation.model

import com.example.cleanarchitecturesolution.features.character.domain.model.Character
import com.example.cleanarchitecturesolution.features.character.domain.model.LocationCharacter

data class CharacterDisplayable(
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val lastLocation: LocationCharacterDisplayable,
    val name: String,
    val originLocation: LocationCharacterDisplayable,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
) {
    constructor(character: Character) : this(
        character.episode,
        character.gender,
        character.id,
        character.image,
        LocationCharacterDisplayable(character.lastLocation),
        character.name,
        LocationCharacterDisplayable(character.originLocation),
        character.species,
        character.status,
        character.type,
        character.url
    )
}

data class LocationCharacterDisplayable(
    val name: String,
    val url: String,
) {
    constructor(locationCharacter: LocationCharacter) : this(
        locationCharacter.name,
        locationCharacter.url
    )
}

data class CharacterUiState(
    val isFetchingCharacter: Boolean = false,
    val characterItems: List<CharacterDisplayable> = listOf(),
    val errorMessage: String? = null,
)

data class CharacterDetailsUiState(
    val isFetchingCharacter: Boolean = false,
    val character: CharacterDisplayable? = null,
    val errorMessage: String? = null,
)
