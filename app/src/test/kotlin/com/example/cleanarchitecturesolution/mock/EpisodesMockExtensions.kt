package com.example.cleanarchitecturesolution.mock

import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeRemote
import com.example.cleanarchitecturesolution.core.data.remote.model.EpisodeResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.ResponseInfo
import com.example.cleanarchitecturesolution.features.episode.data.local.model.EpisodeCached
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode
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

@TestOnly
fun Episode.Companion.mock() = Episode(
    airDate = "test_airDate",
    characters = listOf(),
    code = "test_code",
    id = 1,
    name = "test_name",
    url = "test_url"
)
