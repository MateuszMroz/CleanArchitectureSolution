package com.example.cleanarchitecturesolution.features.episodes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.cleanarchitecturesolution.features.episodes.data.local.model.EpisodeCached

@Dao
interface EpisodesDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveEpisodes(vararg episodes: EpisodeCached)

    @Query("SELECT * FROM episode")
    suspend fun getEpisodes(): List<EpisodeCached>
}
