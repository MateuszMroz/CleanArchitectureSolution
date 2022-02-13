package com.example.cleanarchitecturesolution.features.data.remote.model

import androidx.annotation.Keep
import com.example.cleanarchitecturesolution.features.location.domain.model.Location
import com.google.gson.annotations.SerializedName

@Keep
data class LocationRemote(
    @SerializedName("created") val created: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("residents") val residents: List<String>,
    @SerializedName("type") val type: String,
    @SerializedName("url") val url: String
) {
    fun toLocation(): Location = Location(dimension, id, name, residents, type, url)
}
