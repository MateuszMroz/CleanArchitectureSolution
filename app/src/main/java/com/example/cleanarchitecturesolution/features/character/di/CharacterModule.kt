package com.example.cleanarchitecturesolution.features.character.di

import androidx.recyclerview.widget.DiffUtil.ItemCallback
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import com.example.cleanarchitecturesolution.features.character.CharacterRepository
import com.example.cleanarchitecturesolution.features.character.data.repository.CharacterRepositoryImpl
import com.example.cleanarchitecturesolution.features.character.domain.GetCharactersUseCase
import com.example.cleanarchitecturesolution.features.character.presentation.CharacterFragment
import com.example.cleanarchitecturesolution.features.character.presentation.CharacterViewModel
import com.example.cleanarchitecturesolution.features.character.presentation.adapter.CharacterAdapter
import com.example.cleanarchitecturesolution.features.character.presentation.adapter.CharacterDiffCallback
import com.example.cleanarchitecturesolution.features.character.presentation.model.CharacterDisplayable
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val characterModule = module {
    scope<CharacterFragment> {
        viewModel { CharacterViewModel(get<GetCharactersUseCase>()) }

        scoped<CharacterRepository> {
            CharacterRepositoryImpl(
                get<RickAndMortyApi>(),
                get(),
                get()
            )
        }
        scoped { GetCharactersUseCase(get<CharacterRepository>()) }
        scoped<ItemCallback<CharacterDisplayable>> { CharacterDiffCallback() }
        scoped { CharacterAdapter(get<ItemCallback<CharacterDisplayable>>()) }
    }
}
