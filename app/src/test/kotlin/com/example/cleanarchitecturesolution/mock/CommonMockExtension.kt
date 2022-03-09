package com.example.cleanarchitecturesolution.mock

import com.example.cleanarchitecturesolution.core.data.remote.model.ResponseInfo
import org.jetbrains.annotations.TestOnly

@TestOnly
fun ResponseInfo.Companion.mock() =
    ResponseInfo(count = 0, next = "test_next", pages = 0, prev = "test_prev")
