package com.example.cleanarchitecturesolution.features.location.data.local

import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.cleanarchitecturesolution.features.location.data.local.model.LocationCached

interface LocationDao {

    @Insert(onConflict = REPLACE)
    fun saveLocations(vararg locations: LocationCached)

    @Query("SELECT * FROM location")
    fun getLocations(): List<LocationCached>
}
