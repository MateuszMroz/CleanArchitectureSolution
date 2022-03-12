package com.example.cleanarchitecturesolution.features.episode.domain

import com.example.cleanarchitecturesolution.core.base.UseCase
import com.example.cleanarchitecturesolution.features.episode.EpisodeRepository
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode

class GetEpisodesUseCase(
    private val repository: EpisodeRepository,
) : UseCase<List<Episode>, Unit>() {

    override suspend fun doAction(params: Unit): List<Episode> = repository.fetchEpisodes()
}
