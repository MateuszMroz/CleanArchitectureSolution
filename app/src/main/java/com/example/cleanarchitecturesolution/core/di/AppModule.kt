package com.example.cleanarchitecturesolution.core.di

import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.cleanarchitecturesolution.core.network.NetworkStateProvider
import com.example.cleanarchitecturesolution.core.network.NetworkStateProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val SPAN_COUNT = 2

val appModule = module {
    factory { LinearLayoutManager(get<Context>()) }
    factory { GridLayoutManager(get<Context>(), SPAN_COUNT) }
    factory { DividerItemDecoration(get<Context>(), VERTICAL) }
    factory { androidContext().getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager }
    factory<NetworkStateProvider> { NetworkStateProviderImpl(get()) }
}
