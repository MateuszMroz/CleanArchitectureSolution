package com.example.cleanarchitecturesolution.features.episode.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode

@Entity(tableName = "episode")
data class EpisodeCached(
    @PrimaryKey
    val id: Int,
    val airDate: String,
    val characters: List<String>,
    val code: String,
    val name: String,
    val url: String,
) {
    constructor(episode: Episode) : this(
        episode.id,
        episode.airDate,
        episode.characters,
        episode.code,
        episode.name,
        episode.url
    )

    companion object

    fun toEpisode(): Episode = Episode(airDate, characters, code, id, name, url)
}
