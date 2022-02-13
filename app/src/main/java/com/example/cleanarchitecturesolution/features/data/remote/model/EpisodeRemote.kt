package com.example.cleanarchitecturesolution.features.data.remote.model

import androidx.annotation.Keep
import com.example.cleanarchitecturesolution.features.episodes.domain.model.Episode
import com.google.gson.annotations.SerializedName

@Keep
data class EpisodeRemote(
    @SerializedName("air_date") val airDate: String,
    @SerializedName("characters") val characters: List<String>,
    @SerializedName("created") val created: String,
    @SerializedName("episode") val code: String,
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {
    fun toEpisode(): Episode = Episode(airDate, characters, code, id, name, url)
}
