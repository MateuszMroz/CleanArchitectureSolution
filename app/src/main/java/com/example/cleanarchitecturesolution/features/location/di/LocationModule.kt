package com.example.cleanarchitecturesolution.features.location.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.features.location.data.repository.LocationRepositoryImpl
import com.example.cleanarchitecturesolution.features.location.domain.GetLocationsUseCase
import com.example.cleanarchitecturesolution.features.location.presentation.LocationFragment
import com.example.cleanarchitecturesolution.features.location.presentation.LocationViewModel
import com.example.cleanarchitecturesolution.features.location.presentation.adapter.LocationAdapter
import com.example.cleanarchitecturesolution.features.location.presentation.adapter.LocationDiffCallback
import com.example.cleanarchitecturesolution.features.location.presentation.model.LocationDisplayable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val locationModule = module {
    scope<LocationFragment> {
        viewModel { LocationViewModel(get<GetLocationsUseCase>()) }

        scoped<LocationRepository> { LocationRepositoryImpl(get<RickAndMortyApi>(), get(), get()) }
        scoped { GetLocationsUseCase(get<LocationRepository>()) }
        scoped<ItemCallback<LocationDisplayable>> { LocationDiffCallback() }
        scoped { LocationAdapter(get<ItemCallback<LocationDisplayable>>()) }
    }
}
