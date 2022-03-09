package com.example.cleanarchitecturesolution.mock

import com.example.cleanarchitecturesolution.core.data.remote.model.CharacterRemote
import com.example.cleanarchitecturesolution.core.data.remote.model.CharacterResponse
import com.example.cleanarchitecturesolution.core.data.remote.model.LocationCharacterRemote
import com.example.cleanarchitecturesolution.core.data.remote.model.ResponseInfo
import com.example.cleanarchitecturesolution.features.character.data.local.CharacterCached
import com.example.cleanarchitecturesolution.features.character.data.local.LocationCharacterCached
import org.jetbrains.annotations.TestOnly

@TestOnly
fun LocationCharacterRemote.Companion.mock() = LocationCharacterRemote(
    name = "test_name",
    url = "test_url",
)

@TestOnly
fun CharacterRemote.Companion.mock() = CharacterRemote(
    created = "test_created",
    episode = listOf(),
    gender = "test_gender",
    id = 0,
    image = "test_image",
    lastLocation = LocationCharacterRemote.mock(),
    name = "test_name",
    originLocation = LocationCharacterRemote.mock(),
    species = "test_species",
    status = "test_status",
    type = "test_type",
    url = "test_url",
)

@TestOnly
fun CharacterResponse.Companion.mock() = CharacterResponse(
    info = ResponseInfo.Companion.mock(),
    results = listOf(
        CharacterRemote.mock(),
        CharacterRemote.mock(),
        CharacterRemote.mock(),
    )
)

@TestOnly
fun LocationCharacterCached.Companion.mock() = LocationCharacterCached(
    name = "test_name",
    url = "test_url"
)

@TestOnly
fun CharacterCached.Companion.mock() = CharacterCached(
    id = 0,
    episode = listOf(),
    gender = "test_gender",
    image = "test_image",
    lastLocation = LocationCharacterCached.mock(),
    name = "test_name",
    originLocation = LocationCharacterCached.mock(),
    species = "test_species",
    status = "test_status",
    type = "test_type",
    url = "test_url",
)
