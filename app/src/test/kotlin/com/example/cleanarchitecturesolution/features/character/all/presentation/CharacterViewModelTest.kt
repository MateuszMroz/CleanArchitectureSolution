package com.example.cleanarchitecturesolution.features.character.all.presentation

import androidx.lifecycle.LifecycleOwner
import app.cash.turbine.test
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import com.example.cleanarchitecturesolution.features.character.domain.GetCharactersUseCase
import com.example.cleanarchitecturesolution.features.character.domain.model.Character
import com.example.cleanarchitecturesolution.features.character.navigation.CharacterNavigatorImpl
import com.example.cleanarchitecturesolution.features.location.domain.model.Location
import com.example.cleanarchitecturesolution.mock.mock
import com.example.cleanarchitecturesolution.rules.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.`should be`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@DelicateCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(MainDispatcherRule::class)
internal class CharacterViewModelTest {

    @Test
    fun `WHEN location is clicked THAN open episodeLocationScreen`() {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val characterNavigator = CharacterNavigatorImpl(fragmentNavigator)
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetCharactersUseCase = mockk(relaxed = true)
        val viewModel = CharacterViewModel(useCase, characterNavigator, errorMapper)
        val locationId = 1

        //when
        viewModel.onCharacterClick(locationId)

        verify {
            characterNavigator.openCharacterDetailsScreen(locationId)
        }
    }

    @Test
    fun `WHEN uiState is observed THEN set loading state`() = runTest {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val characterNavigator = CharacterNavigatorImpl(fragmentNavigator)
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetCharactersUseCase = mockk(relaxed = true)
        val viewModel = CharacterViewModel(useCase, characterNavigator, errorMapper)
        val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)

        viewModel.uiState.test {
            viewModel.onCreate(lifecycleOwner)
            // default value
            awaitItem().isFetchingCharacter `should be` false
            // set value on fetch episodes
            awaitItem().isFetchingCharacter `should be` true
        }
    }

    @Test
    fun `WHEN uiState is observed THEN invoke use case to get characters`() = runTest {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val characterNavigator = CharacterNavigatorImpl(fragmentNavigator)
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetCharactersUseCase = mockk(relaxed = true)
        val viewModel = CharacterViewModel(useCase, characterNavigator, errorMapper)
        val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)

        viewModel.onCreate(lifecycleOwner)

        verify {
            useCase(
                params = Unit,
                scope = any(),
                coroutineContext = any(),
                onResult = any()
            )
        }
    }

    @Test
    fun `GIVEN use case result is success WHEN uiState is observed THEN set loading state AND set result in uiState`() =
        runTest {
            val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
            val characterNavigator = CharacterNavigatorImpl(fragmentNavigator)
            val errorMapper = mockk<ErrorMapper>()
            val characters = listOf(Character.mock(), Character.mock(), Character.mock())
            val useCase = mockk<GetCharactersUseCase> {
                every {
                    this@mockk(
                        params = Unit,
                        coroutineContext = any(),
                        scope = any(),
                        onResult = any()
                    )
                } answers {
                    lastArg<(Result<List<Character>>) -> Unit>()(Result.success(characters))
                }
            }
            val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
            val viewModel = CharacterViewModel(useCase, characterNavigator, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingCharacter `should be` false
                // start fetching
                awaitItem().isFetchingCharacter `should be` true
                // stop fetching
                awaitItem().isFetchingCharacter `should be` false
                // success fetching
                awaitItem().characterItems.size `should be equal to` 3
            }
        }

    @Test
    fun `GIVEN use case result is failure WHEN uiState is observed THEN set loading state AND set error message in uiState`() =
        runTest {
            val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
            val characterNavigator = CharacterNavigatorImpl(fragmentNavigator)
            val error = Throwable("Something went wrong.")
            val errorMapper = mockk<ErrorMapper>() {
                every { map(any()) } returns "Something went wrong."
            }
            val useCase = mockk<GetCharactersUseCase> {
                every {
                    this@mockk(
                        params = Unit,
                        coroutineContext = any(),
                        scope = any(),
                        onResult = any()
                    )
                } answers {
                    lastArg<(Result<List<Location>>) -> Unit>()(Result.failure(error))
                }
            }

            val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
            val viewModel = CharacterViewModel(useCase, characterNavigator, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingCharacter `should be` false
                // start fetching
                awaitItem().isFetchingCharacter `should be` true
                // stop fetching
                awaitItem().isFetchingCharacter `should be` false
                // error handle
                awaitItem().errorMessage `should be equal to` "Something went wrong."
            }
        }
}
