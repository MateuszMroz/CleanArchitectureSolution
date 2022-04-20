package com.example.cleanarchitecturesolution.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchitecturesolution.features.character.data.CharacterDao
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached
import com.example.cleanarchitecturesolution.features.episode.data.local.EpisodesDao
import com.example.cleanarchitecturesolution.features.episode.data.local.model.EpisodeCached
import com.example.cleanarchitecturesolution.features.location.data.local.LocationDao
import com.example.cleanarchitecturesolution.features.location.data.local.model.LocationCached

@Database(
    entities = [
        CharacterCached::class,
        EpisodeCached::class,
        LocationCached::class,
    ],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class RickAndMortyDb : RoomDatabase() {

    abstract fun characterDao(): CharacterDao

    abstract fun episodesDao(): EpisodesDao

    abstract fun locationDao(): LocationDao
}
