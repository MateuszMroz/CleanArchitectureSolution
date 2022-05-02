package com.example.cleanarchitecturesolution.features.character.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface CharacterDao {

    @Insert(onConflict = REPLACE)
    fun saveCharacters(vararg characters: CharacterCached)

    @Query("SELECT * FROM character")
    fun getCharacters(): List<CharacterCached>
}
