package com.example.cleanarchitecturesolution.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.cleanarchitecturesolution.features.character.data.CharacterDao
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached
import com.example.cleanarchitecturesolution.features.character.data.local.LocationCharacterCached
import com.example.cleanarchitecturesolution.features.episodes.data.local.EpisodesDao
import com.example.cleanarchitecturesolution.features.episodes.data.local.model.EpisodeCached

@Database(
    entities = [EpisodeCached::class, CharacterCached::class, LocationCharacterCached::class],
    version = 1
)
@TypeConverters(ListConverter::class)
abstract class RickAndMortyDb : RoomDatabase() {

    abstract fun episodesDao(): EpisodesDao

    abstract fun characterDao(): CharacterDao
}
