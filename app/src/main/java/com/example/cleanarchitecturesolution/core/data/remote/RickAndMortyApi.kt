package com.example.cleanarchitecturesolution.core.data.remote

import com.example.cleanarchitecturesolution.core.data.remote.model.CharacterResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.LocationResponse
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(): CharacterResponse

    @GET("episode")
    suspend fun getEpisodes(): EpisodeResponse

    @GET("location")
    suspend fun getLocations(): LocationResponse
}
