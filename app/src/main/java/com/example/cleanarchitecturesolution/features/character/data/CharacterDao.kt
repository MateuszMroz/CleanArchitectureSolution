package com.example.cleanarchitecturesolution.features.character.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached

@Dao
abstract class CharacterDao {

    @Insert(onConflict = REPLACE)
    abstract fun saveCharacters(vararg characters: CharacterCached)

    @Query("SELECT * FROM character")
    abstract fun getCharacters(): List<CharacterCached>
}
