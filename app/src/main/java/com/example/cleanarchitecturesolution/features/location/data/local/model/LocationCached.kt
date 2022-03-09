package com.example.cleanarchitecturesolution.features.location.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.cleanarchitecturesolution.features.location.domain.model.Location

@Entity(tableName = "location")
data class LocationCached(
    @PrimaryKey
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String,
) {
    constructor(location: Location) : this(
        location.dimension,
        location.id,
        location.name,
        location.residents,
        location.type,
        location.url
    )

    companion object

    fun toLocation(): Location = Location(dimension, id, name, residents, type, url)
}
