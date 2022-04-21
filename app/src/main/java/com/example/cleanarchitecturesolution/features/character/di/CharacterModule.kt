package com.example.cleanarchitecturesolution.features.character.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigatorImpl
import com.example.cleanarchitecturesolution.features.character.CharacterRepository
import com.example.cleanarchitecturesolution.features.character.all.presentation.CharacterFragment
import com.example.cleanarchitecturesolution.features.character.all.presentation.CharacterViewModel
import com.example.cleanarchitecturesolution.features.character.all.presentation.adapter.CharacterAdapter
import com.example.cleanarchitecturesolution.features.character.all.presentation.adapter.CharacterDiffCallback
import com.example.cleanarchitecturesolution.features.character.all.presentation.model.CharacterDisplayable
import com.example.cleanarchitecturesolution.features.character.data.repository.CharacterRepositoryImpl
import com.example.cleanarchitecturesolution.features.character.details.CharacterDetailsFragment
import com.example.cleanarchitecturesolution.features.character.details.CharacterDetailsViewModel
import com.example.cleanarchitecturesolution.features.character.domain.GetCharactersUseCase
import com.example.cleanarchitecturesolution.features.character.navigation.CharacterNavigator
import com.example.cleanarchitecturesolution.features.character.navigation.CharacterNavigatorImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterModule = module {
    scope<CharacterDetailsFragment> {
        viewModel { params ->
            CharacterDetailsViewModel(params.get())
        }

        scoped { CharacterDetailsFragment() }
    }


    scope<CharacterFragment> {
        viewModel {
            CharacterViewModel(
                get<GetCharactersUseCase>(),
                get<CharacterNavigator>(),
                get<ErrorMapper>()
            )
        }

        scoped<CharacterRepository> {
            CharacterRepositoryImpl(
                get<RickAndMortyApi>(),
                get(),
                get(),
                get()
            )
        }
        scoped { GetCharactersUseCase(get<CharacterRepository>()) }
        scoped<ItemCallback<CharacterDisplayable>> { CharacterDiffCallback() }
        scoped { CharacterAdapter(get<ItemCallback<CharacterDisplayable>>()) }
        scoped<CharacterNavigator> { CharacterNavigatorImpl(get<FragmentNavigatorImpl>()) }
    }
}
