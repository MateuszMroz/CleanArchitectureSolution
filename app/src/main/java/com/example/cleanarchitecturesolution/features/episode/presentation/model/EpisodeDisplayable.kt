package com.example.cleanarchitecturesolution.features.episode.presentation.model

import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode

data class EpisodeDisplayable(
    val airDate: String,
    val characters: List<String>,
    val code: String,
    val id: Int,
    val name: String,
    val url: String,
) {
    constructor(episode: Episode) : this(
        episode.airDate,
        episode.characters,
        episode.code,
        episode.id,
        episode.name,
        episode.url
    )
}

data class EpisodeUiState(
    val isFetchingEpisode: Boolean = false,
    val episodeItems: List<EpisodeDisplayable> = listOf(),
    val errorMessage: String? = null
)
