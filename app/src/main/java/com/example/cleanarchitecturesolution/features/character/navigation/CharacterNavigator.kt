package com.example.cleanarchitecturesolution.features.character.navigation

import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigator
import com.example.cleanarchitecturesolution.features.character.details.CharacterDetailsFragment.Companion.CHARACTER_DETAILS_KEY

interface CharacterNavigator {
    fun openCharacterDetailsScreen(characterId: Int)
    fun goBack()
}

class CharacterNavigatorImpl(
    private val fragmentNavigator: FragmentNavigator,
) : CharacterNavigator {

    override fun openCharacterDetailsScreen(characterId: Int) {
        fragmentNavigator.navigateTo(
            R.id.action_navigate_to_character_details_screen,
            CHARACTER_DETAILS_KEY to characterId
        )
    }

    override fun goBack() {
        fragmentNavigator.goBack()
    }
}
