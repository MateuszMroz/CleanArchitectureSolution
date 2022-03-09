package com.example.cleanarchitecturesolution.mock

import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeRemote
import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.ResponseInfo
import com.example.cleanarchitecturesolution.features.episodes.data.local.model.EpisodeCached
import org.jetbrains.annotations.TestOnly

@TestOnly
fun EpisodeRemote.Companion.mock() = EpisodeRemote(
    airDate = "test_airDate",
    characters = listOf(),
    created = "test_created",
    code = "test_code",
    id = 0,
    name = "test_name",
    url = "test_url"
)

@TestOnly
fun EpisodeResponse.Companion.mock() = EpisodeResponse(
    info = ResponseInfo.mock(),
    results = listOf(
        EpisodeRemote.mock(),
        EpisodeRemote.mock(),
        EpisodeRemote.mock(),
    )
)

@TestOnly
fun EpisodeCached.Companion.mock() = EpisodeCached(
    id = 0,
    name = "test_name",
    airDate = "test_airData",
    code = "test_code",
    characters = emptyList(),
    url = "test_url"
)
