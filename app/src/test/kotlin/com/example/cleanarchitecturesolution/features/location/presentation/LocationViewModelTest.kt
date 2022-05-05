package com.example.cleanarchitecturesolution.features.location.presentation

import androidx.lifecycle.LifecycleOwner
import app.cash.turbine.test
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.features.location.domain.GetLocationsUseCase
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
internal class LocationViewModelTest {

    @Test
    fun `WHEN uiState is observed THEN set loading state`() = runTest {
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetLocationsUseCase = mockk(relaxed = true)
        val viewModel = LocationViewModel(useCase, errorMapper)
        val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)

        viewModel.uiState.test {
            viewModel.onCreate(lifecycleOwner)

            // default value
            awaitItem().isFetchingLocation `should be` false
            // set value on fetch episodes
            awaitItem().isFetchingLocation `should be` true
        }
    }

    @Test
    fun `WHEN uiState is observed THEN invoke use case to get locations`() = runTest {
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetLocationsUseCase = mockk(relaxed = true)
        val viewModel = LocationViewModel(useCase, errorMapper)
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
            val errorMapper = mockk<ErrorMapper>()
            val locations = listOf(Location.mock(), Location.mock(), Location.mock())
            val useCase = mockk<GetLocationsUseCase> {
                every {
                    this@mockk(
                        params = Unit,
                        coroutineContext = any(),
                        scope = any(),
                        onResult = any()
                    )
                } answers {
                    lastArg<(Result<List<Location>>) -> Unit>()(Result.success(locations))
                }
            }

            val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
            val viewModel = LocationViewModel(useCase, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingLocation `should be` false
                // start fetching
                awaitItem().isFetchingLocation `should be` true
                // stop fetching
                awaitItem().isFetchingLocation `should be` false
                // success fetching
                awaitItem().locationItems.size `should be equal to` 3
            }
        }

    @Test
    fun `GIVEN use case result is failure WHEN uiState is observed THEN set loading state AND set error message in uiState`() =
        runTest {
            val error = Throwable()
            val errorMapper = mockk<ErrorMapper>() {
                every { map(any()) } returns "Something went wrong."
            }
            val useCase = mockk<GetLocationsUseCase> {
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
            val viewModel = LocationViewModel(useCase, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingLocation `should be` false
                // start fetching
                awaitItem().isFetchingLocation `should be` true
                // stop fetching
                awaitItem().isFetchingLocation `should be` false
                // error handle
                awaitItem().errorMessage `should be equal to` "Something went wrong."
            }
        }
}
