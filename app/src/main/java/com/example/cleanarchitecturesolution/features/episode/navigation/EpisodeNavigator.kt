package com.example.cleanarchitecturesolution.features.episode.navigation

import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import com.example.cleanarchitecturesolution.features.episode.details.presentation.EpisodeDetailsFragment.Companion.EPISODES_DETAILS_KEY


interface EpisodeNavigator {
    fun openEpisodeDetailsScreen(episodeId: Int)
    fun goBack()
}

class EpisodeNavigatorImpl(private val fragmentNavigator: FragmentNavigator) : EpisodeNavigator {

    override fun openEpisodeDetailsScreen(episodeId: Int) {
        fragmentNavigator.navigateTo(
            R.id.action_navigate_to_episode_details_screen,
            EPISODES_DETAILS_KEY to episodeId
        )
    }

    override fun goBack() {
        fragmentNavigator.goBack()
    }

}


