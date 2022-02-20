package com.example.cleanarchitecturesolution.features.location.domain

import com.example.cleanarchitecturesolution.features.location.LocationRepository
import com.example.cleanarchitecturesolution.rules.MainDispatcherRule
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
internal class GetLocationsUseCaseTest {

    @Test
    fun `when use case is invoked verify if fetchLocations method from repository was triggered`() =
        runTest {
            val repository: LocationRepository = mockk(relaxed = true)
            val useCase = GetLocationsUseCase(repository)

            useCase(
                params = Unit,
                scope = MainScope()
            )

            coVerify { repository.fetchLocations() }
        }
}
