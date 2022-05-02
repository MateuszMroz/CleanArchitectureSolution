package com.example.cleanarchitecturesolution.core.di

import com.example.cleanarchitecturesolution.features.character.di.characterModule
import com.example.cleanarchitecturesolution.features.episode.di.episodeModule
import com.example.cleanarchitecturesolution.features.location.di.locationModule
import org.koin.core.module.Module

val featureModules: List<Module> = listOf(
    episodeModule,
    locationModule,
    characterModule
)
