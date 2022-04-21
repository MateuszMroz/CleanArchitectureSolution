package com.example.cleanarchitecturesolution.features.episode.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.features.episode.EpisodeRepository
import com.example.cleanarchitecturesolution.features.episode.data.repository.EpisodeRepositoryImpl
import com.example.cleanarchitecturesolution.features.episode.domain.GetEpisodesUseCase
import com.example.cleanarchitecturesolution.features.episode.presentation.EpisodeFragment
import com.example.cleanarchitecturesolution.features.episode.presentation.EpisodeViewModel
import com.example.cleanarchitecturesolution.features.episode.presentation.adapter.EpisodeAdapter
import com.example.cleanarchitecturesolution.features.episode.presentation.adapter.EpisodeDiffCallback
import com.example.cleanarchitecturesolution.features.episode.presentation.model.EpisodeDisplayable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeModule = module {
    scope<EpisodeFragment> {
        viewModel { EpisodeViewModel(get<GetEpisodesUseCase>()) }

        scoped<EpisodeRepository> { EpisodeRepositoryImpl(get<RickAndMortyApi>(), get(), get()) }
        scoped { GetEpisodesUseCase(get<EpisodeRepository>()) }
        scoped<ItemCallback<EpisodeDisplayable>> { EpisodeDiffCallback() }
        scoped { EpisodeAdapter(get<ItemCallback<EpisodeDisplayable>>()) }
    }
}
