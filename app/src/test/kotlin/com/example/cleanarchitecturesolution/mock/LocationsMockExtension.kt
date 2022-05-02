package com.example.cleanarchitecturesolution.mock

import com.example.cleanarchitecturesolution.core.data.remote.model.LocationRemote
import com.example.cleanarchitecturesolution.core.data.remote.model.LocationResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.ResponseInfo
import com.example.cleanarchitecturesolution.features.location.data.local.model.LocationCached
import com.example.cleanarchitecturesolution.features.location.domain.model.Location
import org.jetbrains.annotations.TestOnly

@TestOnly
fun LocationRemote.Companion.mock() = LocationRemote(
    created = "test_created",
    dimension = "test_dimension",
    id = 0,
    name = "test_name",
    residents = listOf(),
    type = "test_type",
    url = "test_url"
)

@TestOnly
fun LocationResponse.Companion.mock() = LocationResponse(
    info = ResponseInfo.mock(),
    results = listOf(
        LocationRemote.mock(),
        LocationRemote.mock(),
        LocationRemote.mock(),
    )
)

@TestOnly
fun LocationCached.Companion.mock() = LocationCached(
    id = 0,
    dimension = "test_dimension",
    name = "test_name",
    residents = listOf(),
    type = "test_type",
    url = "test_url"
)

@TestOnly
fun Location.Companion.mock() = Location(
    dimension = "test_dimension",
    id = 0,
    name = "test_name",
    residents = listOf(),
    type = "test_type",
    url = "test_url"
)
