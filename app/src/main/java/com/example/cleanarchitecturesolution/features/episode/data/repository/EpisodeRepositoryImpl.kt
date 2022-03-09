package com.example.cleanarchitecturesolution.features.episode.data.repository

import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.features.episode.EpisodeRepository
import com.example.cleanarchitecturesolution.features.episode.data.local.EpisodesDao
import com.example.cleanarchitecturesolution.features.episode.data.local.model.EpisodeCached
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode

class EpisodeRepositoryImpl(
    private val api: RickAndMortyApi,
    private val episodesDao: EpisodesDao,
    private val networkStateProvider: NetworkStateProvider,
) : EpisodeRepository {

    override suspend fun fetchEpisodes(): List<Episode> {
        return if (networkStateProvider.isNetworkAvailable()) {
            getEpisodesFromRemote()
                .also { saveEpisodesToLocal(it) }
        } else {
            getEpisodesFromLocal()
        }
    }

    private suspend fun getEpisodesFromRemote(): List<Episode> {
        return api.getEpisodes()
            .results
            .map { it.toEpisode() }
    }

    private suspend fun getEpisodesFromLocal(): List<Episode> {
        return episodesDao.getEpisodes()
            .map { it.toEpisode() }
    }

    private suspend fun saveEpisodesToLocal(episodes: List<Episode>) {
        episodes.map { EpisodeCached(it) }
            .toTypedArray()
            .let { episodesDao.saveEpisodes(*it) }
    }
}
