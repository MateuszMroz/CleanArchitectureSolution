package com.example.cleanarchitecturesolution.core.di

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.navigation.navOptions
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.cleanarchitecturesolution.R
import com.example.cleanarchitecturesolution.core.exception.ErrorMapper
import com.example.cleanarchitecturesolution.core.exception.ErrorMapperImpl
import com.example.cleanarchitecturesolution.core.exception.ErrorWrapper
import com.example.cleanarchitecturesolution.core.exception.ErrorWrapperImpl
import com.example.cleanarchitecturesolution.core.navigation.FragmentNavigatorImpl
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.core.network.NetworkStateProviderImpl
import com.example.cleanarchitecturesolution.core.provider.ActivityProvider
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SPAN_COUNT = 2

val appModule = module {
    factory { LinearLayoutManager(get<Context>()) }
    factory { GridLayoutManager(get<Context>(), SPAN_COUNT) }
    factory { DividerItemDecoration(get<Context>(), VERTICAL) }

    factory { androidContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }
    factory<NetworkStateProvider> { NetworkStateProviderImpl(get()) }

    factory<ErrorWrapper> { ErrorWrapperImpl() }
    factory<ErrorMapper> { ErrorMapperImpl(androidContext()) }

    factory {
        FragmentNavigatorImpl(
            activityProvider = get(),
            navHostFragmentRes = R.id.nav_host_fragment,
            homeDestinationRes = R.id.characters,
            defaultNavOptions = get()
        )
    }
    factory {
        navOptions {
            anim { enter = R.anim.nav_default_enter_anim }
            anim { exit = R.anim.nav_default_exit_anim }
            anim { popEnter = R.anim.nav_default_pop_enter_anim }
            anim { popExit = R.anim.nav_default_pop_exit_anim }
        }
    }
    single(createdAtStart = true) { ActivityProvider(androidApplication()) }
}
