package com.example.cleanarchitecturesolution.features.location.navigation

import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import com.example.cleanarchitecturesolution.features.location.details.LocationDetailsFragment.Companion.LOCATION_DETAILS_KEY

interface LocationNavigator {
    fun openLocationDetailsScreen(locationId: Int)
    fun goBack()
}

class LocationNavigatorImpl(private val fragmentNavigator: FragmentNavigator): LocationNavigator {

    override fun openLocationDetailsScreen(locationId: Int) {
        fragmentNavigator.navigateTo(
            R.id.action_navigate_to_location_details_screen,
            LOCATION_DETAILS_KEY to locationId
        )
    }

    override fun goBack() {
        fragmentNavigator.goBack()
    }
}
