package com.example.cleanarchitecturesolution.features.character.domain

import com.example.cleanarchitecturesolution.features.character.CharacterRepository
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
internal class GetCharactersUseCaseTest {

    @Test
    fun `when use case is invoked verify if fetchCharacters method from repository was triggered`() =
        runTest {
            val repository: CharacterRepository = mockk(relaxed = true)
            val useCase = GetCharactersUseCase(repository)

            useCase(
                params = Unit,
                scope = MainScope()
            )

            coVerify { repository.fetchCharacters() }
        }
}
