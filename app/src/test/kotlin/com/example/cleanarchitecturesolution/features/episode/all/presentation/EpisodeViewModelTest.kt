package com.example.cleanarchitecturesolution.features.episode.all.presentation

import androidx.lifecycle.LifecycleOwner
import app.cash.turbine.test
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import com.example.cleanarchitecturesolution.features.episode.domain.GetEpisodesUseCase
import com.example.cleanarchitecturesolution.features.episode.domain.model.Episode
import com.example.cleanarchitecturesolution.features.episode.navigation.EpisodeNavigator
import com.example.cleanarchitecturesolution.features.episode.navigation.EpisodeNavigatorImpl
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
internal class EpisodeViewModelTest {

    @Test
    fun `WHEN episode is clicked THAN open episodeDetailsScreen`() {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val episodeNavigator = EpisodeNavigatorImpl(fragmentNavigator)
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetEpisodesUseCase = mockk(relaxed = true)
        val viewModel = EpisodeViewModel(useCase, episodeNavigator, errorMapper)
        val episodeId = 1

        //when
        viewModel.onEpisodeClick(episodeId)

        verify {
            episodeNavigator.openEpisodeDetailsScreen(episodeId)
        }
    }

    @Test
    fun `WHEN uiState is observed THEN set loading state`() = runTest {
        val episodeNavigator = mockk<EpisodeNavigator>()
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetEpisodesUseCase = mockk(relaxed = true)
        val viewModel = EpisodeViewModel(useCase, episodeNavigator, errorMapper)
        val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)

        viewModel.uiState.test {
            viewModel.onCreate(lifecycleOwner)

            // default value
            awaitItem().isFetchingEpisode `should be` false
            // set value on fetch episodes
            awaitItem().isFetchingEpisode `should be` true
        }
    }

    @Test
    fun `WHEN uiState is observed THEN invoke use case to get episodes`() = runTest {
        val episodeNavigator = mockk<EpisodeNavigator>()
        val errorMapper = mockk<ErrorMapper>()
        val useCase: GetEpisodesUseCase = mockk(relaxed = true)
        val viewModel = EpisodeViewModel(useCase, episodeNavigator, errorMapper)
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
            val episodeNavigator = mockk<EpisodeNavigator>()
            val errorMapper = mockk<ErrorMapper>()
            val episodes = listOf(Episode.mock(), Episode.mock(), Episode.mock())
            val useCase = mockk<GetEpisodesUseCase> {
                every {
                    this@mockk(
                        params = Unit,
                        coroutineContext = any(),
                        scope = any(),
                        onResult = any()
                    )
                } answers {
                    lastArg<(Result<List<Episode>>) -> Unit>()(Result.success(episodes))
                }
            }

            val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
            val viewModel = EpisodeViewModel(useCase, episodeNavigator, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingEpisode `should be` false
                // start fetching
                awaitItem().isFetchingEpisode `should be` true
                // stop fetching
                awaitItem().isFetchingEpisode `should be` false
                // success fetching
                awaitItem().episodeItems.size `should be equal to` 3
            }
        }


    @Test
    fun `GIVEN use case result is failure WHEN uiState is observed THEN set loading state AND set error message in uiState`() =
        runTest {
            val episodeNavigator = mockk<EpisodeNavigator>()
            val error = Throwable("Something went wrong.")
            val errorMapper = mockk<ErrorMapper>() {
                every { map(any()) } returns "Something went wrong."
            }
            val useCase = mockk<GetEpisodesUseCase> {
                every {
                    this@mockk(
                        params = Unit,
                        coroutineContext = any(),
                        scope = any(),
                        onResult = any()
                    )
                } answers {
                    lastArg<(Result<List<Episode>>) -> Unit>()(Result.failure(error))
                }
            }

            val lifecycleOwner: LifecycleOwner = mockk(relaxed = true)
            val viewModel = EpisodeViewModel(useCase, episodeNavigator, errorMapper)

            viewModel.uiState.test {
                viewModel.onCreate(lifecycleOwner)

                // default value
                awaitItem().isFetchingEpisode `should be` false
                // start fetching
                awaitItem().isFetchingEpisode `should be` true
                // stop fetching
                awaitItem().isFetchingEpisode `should be` false
                // error handle
                awaitItem().errorMessage `should be equal to` "Something went wrong."
            }
        }
}
