package com.example.cleanarchitecturesolution.features.episode.navigation

import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

internal class EpisodeNavigatorImplTest {

    @Test
    fun `WHEN openEpisodeDetailsScreen is called THAN invoke navigateTo method of FragmentNavigator with appropriate action and episode id as argument`() {
        val episodeId = 1
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val episodeNavigator: EpisodeNavigator = EpisodeNavigatorImpl(fragmentNavigator)
        val slot = slot<Pair<String, Int>>()
        //when
        episodeNavigator.openEpisodeDetailsScreen(episodeId)

        //then
        verify {
            fragmentNavigator.navigateTo(
                R.id.action_navigate_to_episode_details_screen,
                capture(slot)
            )
        }
        slot.captured.second `should be equal to` episodeId
    }

    @Test
    fun `WHEN goBAck is called THAN invoke goBack method of FragmentNavigator`() {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val episodeNavigator: EpisodeNavigator = EpisodeNavigatorImpl(fragmentNavigator)
        //when
        episodeNavigator.goBack()

        //then
        verify {
            fragmentNavigator.goBack()
        }
    }
}
