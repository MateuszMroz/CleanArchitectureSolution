package com.example.cleanarchitecturesolution.features.location.navigation

import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.`should be equal to`
import org.junit.jupiter.api.Test

internal class LocationNavigatorImplTest {

    @Test
    fun `WHEN openLocationDetailsScreen is called THAN invoke navigateTo method of FragmentNavigator with appropriate action and location id as argument`() {
        val locationId = 1
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val locationNavigator: LocationNavigator = LocationNavigatorImpl(fragmentNavigator)
        val slot = slot<Pair<String, Int>>()

        //when
        locationNavigator.openLocationDetailsScreen(locationId)

        //then
        verify {
            fragmentNavigator.navigateTo(
                R.id.action_navigate_to_location_details_screen,
                capture(slot)
            )
        }
        slot.captured.second `should be equal to` locationId
    }

    @Test
    fun `WHEN goBAck is called THAN invoke goBack method of FragmentNavigator`() {
        val fragmentNavigator = mockk<FragmentNavigator>(relaxed = true)
        val locationNavigator: LocationNavigator = LocationNavigatorImpl(fragmentNavigator)

        //when
        locationNavigator.goBack()

        //then
        verify {
            fragmentNavigator.goBack()
        }
    }
}
