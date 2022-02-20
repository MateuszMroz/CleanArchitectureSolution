package com.example.cleanarchitecturesolution.features.rules

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
class MainDispatcherRule : AfterEachCallback, BeforeEachCallback {
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    override fun beforeEach(context: ExtensionContext?) {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    override fun afterEach(context: ExtensionContext?) {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }
}
