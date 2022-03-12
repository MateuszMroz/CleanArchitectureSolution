package com.example.cleanarchitecturesolution.core.data.remote.model

import androidx.annotation.Keep
import com.example.cleanarchitecturesolution.features.character.domain.model.Character
import com.example.cleanarchitecturesolution.features.character.domain.model.LocationCharacter
import com.google.gson.annotations.SerializedName

@Keep
data class CharacterResponse(
    @SerializedName("info") val info: ResponseInfo,
    @SerializedName("results") val results: List<CharacterRemote>,
) {
    companion object
}

@Keep
data class CharacterRemote(
    @SerializedName("created") val created: String,
    @SerializedName("episode") val episode: List<String>,
    @SerializedName("gender") val gender: String,
    @SerializedName("id") val id: Int,
    @SerializedName("image") val image: String,
    @SerializedName("location") val lastLocation: LocationCharacterRemote,
    @SerializedName("name") val name: String,
    @SerializedName("origin") val originLocation: LocationCharacterRemote,
    @SerializedName("species") val species: String,
    @SerializedName("status") val status: String,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String,
) {
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

@Keep
data class LocationCharacterRemote(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String,
) {
    companion object

    fun toLocationCharacter() = LocationCharacter(name, url)
}
