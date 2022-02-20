package com.example.cleanarchitecturesolution.features.episodes.domain

import com.example.cleanarchitecturesolution.features.episodes.EpisodesRepository
import com.example.cleanarchitecturesolution.features.rules.MainDispatcherRule
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
internal class GetEpisodesUseCaseTest {

    @Test
    fun `when use case is invoked verify if fetchEpisodes method from repository was triggered`() =
        runTest {
            val repository: EpisodesRepository = mockk(relaxed = true)
            val useCase = GetEpisodesUseCase(repository)

            useCase(
                params = Unit,
                scope = MainScope()
            )

            coVerify { repository.fetchEpisodes() }
        }
}
