package com.example.cleanarchitecturesolution.features.episode.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigatorImpl
import com.example.cleanarchitecturesolution.features.episode.EpisodeRepository
import com.example.cleanarchitecturesolution.features.episode.all.presentation.EpisodeFragment
import com.example.cleanarchitecturesolution.features.episode.all.presentation.EpisodeViewModel
import com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter.EpisodeAdapter
import com.example.cleanarchitecturesolution.features.episode.all.presentation.adapter.EpisodeDiffCallback
import com.example.cleanarchitecturesolution.features.episode.all.presentation.model.EpisodeDisplayable
import com.example.cleanarchitecturesolution.features.episode.data.repository.EpisodeRepositoryImpl
import com.example.cleanarchitecturesolution.features.episode.details.presentation.EpisodeDetailsFragment
import com.example.cleanarchitecturesolution.features.episode.details.presentation.EpisodeDetailsViewModel
import com.example.cleanarchitecturesolution.features.episode.domain.GetEpisodesUseCase
import com.example.cleanarchitecturesolution.features.episode.navigation.EpisodeNavigator
import com.example.cleanarchitecturesolution.features.episode.navigation.EpisodeNavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val episodeModule = module {
    scope<EpisodeDetailsFragment> {
        viewModel { params ->
            EpisodeDetailsViewModel(params.get())
        }

        scoped { EpisodeDetailsFragment() }
    }

    scope<EpisodeFragment> {
        viewModel {
            EpisodeViewModel(
                get<GetEpisodesUseCase>(),
                get<EpisodeNavigator>(),
                get<ErrorMapper>())
        }

        scoped<EpisodeRepository> {
            EpisodeRepositoryImpl(get<RickAndMortyApi>(),
                get(),
                get(),
                get())
        }
        scoped { GetEpisodesUseCase(get<EpisodeRepository>()) }
        scoped<ItemCallback<EpisodeDisplayable>> { EpisodeDiffCallback() }
        scoped { EpisodeAdapter(get<ItemCallback<EpisodeDisplayable>>()) }
        scoped<EpisodeNavigator> { EpisodeNavigatorImpl(get<FragmentNavigatorImpl>()) }
    }
}
