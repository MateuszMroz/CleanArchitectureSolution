package com.example.cleanarchitecturesolution.core.di

import androidx.room.Room
import com.example.cleanarchitecturesolution.core.data.local.RickAndMortyDb
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            RickAndMortyDb::class.java, "rick-and-morty"
        ).build()
    }

    single {
        get<RickAndMortyDb>().episodesDao()
    }
}
