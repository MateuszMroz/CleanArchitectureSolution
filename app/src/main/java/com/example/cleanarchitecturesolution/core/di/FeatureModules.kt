package com.example.cleanarchitecturesolution.core.di

import com.example.cleanarchitecturesolution.features.episode.di.episodeModule
import org.koin.core.module.Module

val featureModules: List<Module> = listOf(
    episodeModule
)
