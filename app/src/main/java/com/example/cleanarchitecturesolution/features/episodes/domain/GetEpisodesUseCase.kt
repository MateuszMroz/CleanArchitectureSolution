package com.example.cleanarchitecturesolution.features.episodes.domain

import com.example.cleanarchitecturesolution.core.base.UseCase
import com.example.cleanarchitecturesolution.features.episodes.EpisodesRepository
import com.example.cleanarchitecturesolution.features.episodes.domain.model.Episode

class GetEpisodesUseCase(
    private val repository: EpisodesRepository
) : UseCase<List<Episode>, Unit>() {

    override suspend fun doAction(params: Unit): List<Episode> = repository.fetchEpisodes()
}
