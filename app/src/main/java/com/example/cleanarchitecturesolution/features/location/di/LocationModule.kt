package com.example.cleanarchitecturesolution.features.location.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigatorImpl
import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.features.location.all.presentation.LocationFragment
import com.example.cleanarchitecturesolution.features.location.all.presentation.LocationViewModel
import com.example.cleanarchitecturesolution.features.location.all.presentation.adapter.LocationAdapter
import com.example.cleanarchitecturesolution.features.location.all.presentation.adapter.LocationDiffCallback
import com.example.cleanarchitecturesolution.features.location.all.presentation.model.LocationDisplayable
import com.example.cleanarchitecturesolution.features.location.data.repository.LocationRepositoryImpl
import com.example.cleanarchitecturesolution.features.location.details.LocationDetailsFragment
import com.example.cleanarchitecturesolution.features.location.details.LocationDetailsViewModel
import com.example.cleanarchitecturesolution.features.location.domain.GetLocationsUseCase
import com.example.cleanarchitecturesolution.features.location.navigation.LocationNavigator
import com.example.cleanarchitecturesolution.features.location.navigation.LocationNavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationModule = module {
    scope<LocationDetailsFragment> {
        viewModel { params ->
            LocationDetailsViewModel(params.get())
        }

        scoped { LocationDetailsFragment() }
    }

    scope<LocationFragment> {
        viewModel {
            LocationViewModel(
                get<GetLocationsUseCase>(),
                get<LocationNavigator>(),
                get<ErrorMapper>()
            )
        }

        scoped<LocationRepository> {
            LocationRepositoryImpl(
                get<RickAndMortyApi>(),
                get(),
                get(),
                get()
            )
        }
        scoped { GetLocationsUseCase(get<LocationRepository>()) }
        scoped<ItemCallback<LocationDisplayable>> { LocationDiffCallback() }
        scoped { LocationAdapter(get<ItemCallback<LocationDisplayable>>()) }
        scoped<LocationNavigator> { LocationNavigatorImpl(get<FragmentNavigatorImpl>()) }
    }
}
