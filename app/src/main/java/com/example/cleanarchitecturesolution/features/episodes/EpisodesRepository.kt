package com.example.cleanarchitecturesolution.features.episodes

import com.example.cleanarchitecturesolution.features.episodes.domain.model.Episode

interface EpisodesRepository {
    fun fetchEpisodes(): List<Episode>
}
