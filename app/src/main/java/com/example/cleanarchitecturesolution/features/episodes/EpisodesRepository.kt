package com.example.cleanarchitecturesolution.features.episodes

import com.example.cleanarchitecturesolution.features.episodes.domain.model.Episode

interface EpisodesRepository {
    suspend fun fetchEpisodes(): List<Episode>
}
