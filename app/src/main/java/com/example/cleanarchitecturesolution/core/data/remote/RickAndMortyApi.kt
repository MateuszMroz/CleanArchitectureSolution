package com.example.cleanarchitecturesolution.core.data.remote

import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeResponse
import retrofit2.http.GET

interface RickAndMortyApi {

    @GET("episode")
    suspend fun getEpisodes(): EpisodeResponse
}
