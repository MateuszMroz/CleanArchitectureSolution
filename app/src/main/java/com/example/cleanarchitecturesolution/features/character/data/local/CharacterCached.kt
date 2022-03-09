package com.example.cleanarchitecturesolution.features.character.data.local

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleanarchitecturesolution.features.character.domain.model.Character
import com.example.cleanarchitecturesolution.features.character.domain.model.LocationCharacter

@Entity(tableName = "character")
class CharacterCached(
    @PrimaryKey
    val id: Int,
    val episode: List<String>,
    val gender: String,
    val image: String,
    @Embedded
    val lastLocation: LocationCharacterCached,
    val name: String,
    @Embedded
    val originLocation: LocationCharacterCached,
    val species: String,
    val status: String,
    val type: String,
    val url: String,
) {

    constructor(character: Character) : this(
        character.id,
        character.episode,
        character.gender,
        character.image,
        LocationCharacterCached(character.lastLocation),
        character.name,
        LocationCharacterCached(character.originLocation),
        character.species,
        character.status,
        character.type,
        character.url
    )

    companion object

    fun toCharacter(): Character = Character(
        episode,
        gender,
        id,
        image,
        lastLocation.toLocationCharacter(),
        name,
        originLocation.toLocationCharacter(),
        species,
        status,
        type,
        url
    )
}

@Entity(tableName = "location")
data class LocationCharacterCached(
    val name: String,
    val url: String,
) {

    constructor(locationCharacter: LocationCharacter) : this(
        locationCharacter.name,
        locationCharacter.url
    )

    companion object

    fun toLocationCharacter() = LocationCharacter(name, url)
}
