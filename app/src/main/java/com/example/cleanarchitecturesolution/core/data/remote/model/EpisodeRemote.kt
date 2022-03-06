package com.example.cleanarchitecturesolution.core.data.remote.model

import androidx.annotation.Keep
import com.example.cleanarchitecturesolution.features.episodes.domain.model.Episode
import com.google.gson.annotations.SerializedName

@Keep
data class EpisodeResponse(
    @SerializedName("info") val info: ResponseInfo,
    @SerializedName("results") val results: List<EpisodeRemote>
) {
    companion object
}

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
    companion object

    fun toEpisode(): Episode = Episode(airDate, characters, code, id, name, url)
}

@Keep
data class ResponseInfo(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("pages") val pages: Int,
    @SerializedName("prev") val prev: String
){
    companion object
}
