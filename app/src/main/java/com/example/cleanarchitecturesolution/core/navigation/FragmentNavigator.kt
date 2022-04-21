package com.example.cleanarchitecturesolution.core.navigation

import androidx.annotation.IdRes
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import com.example.cleanarchitecturesolution.core.provider.ActivityProvider

interface FragmentNavigator {

    fun navigateTo(
        @IdRes destinationId: Int,
        fragmentTransaction: FragmentTransition? = null,
    )

    fun <T> navigateTo(
        @IdRes destinationId: Int,
        param: Pair<String, T>? = null,
        fragmentTransaction: FragmentTransition? = null,
    )

    fun goBack(@IdRes destinationId: Int? = null, inclusive: Boolean = false)

    fun clearHistory()
}

class FragmentNavigatorImpl(
    private val activityProvider: ActivityProvider,
    @IdRes private val navHostFragmentRes: Int,
    @IdRes private val homeDestinationRes: Int,
    private val defaultNavOptions: NavOptions,
) : FragmentNavigator {

    private fun getSupportFragmentManager() =
        (activityProvider.foregroundActivity as? FragmentActivity)?.supportFragmentManager

    private fun getNavController(): NavController? {
        val navHostFragment =
            getSupportFragmentManager()?.findFragmentById(navHostFragmentRes) as? NavHostFragment
        return navHostFragment?.navController
    }

    override fun navigateTo(
        destinationId: Int,
        fragmentTransaction: FragmentTransition?,
    ) {
        navigateTo<Unit>(destinationId, null, fragmentTransaction)
    }

    override fun <T> navigateTo(
        destinationId: Int,
        param: Pair<String, T>?,
        fragmentTransaction: FragmentTransition?,
    ) {
        val bundle = param?.let { bundleOf(it) }
        val navOptions = fragmentTransaction?.let {
            navOptions {
                anim { enter = it.enterAnim }
                anim { exit = it.exitAnim }
                anim { popEnter = it.popEnterAnim }
                anim { popExit = it.popExitAnim }
            }
        } ?: defaultNavOptions
        val navController = getNavController()
        navController?.navigate(destinationId, bundle, navOptions)
    }

    override fun goBack(destinationId: Int?, inclusive: Boolean) {
        if (destinationId == null) getNavController()?.popBackStack()
        else getNavController()?.popBackStack(destinationId, inclusive)
    }

    override fun clearHistory() {
        goBack(homeDestinationRes)
    }

}
