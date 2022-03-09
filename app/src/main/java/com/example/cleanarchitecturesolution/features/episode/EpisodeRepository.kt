package com.example.cleanarchitecturesolution.features.episode

import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode

interface EpisodeRepository {
    suspend fun fetchEpisodes(): List<Episode>
}
