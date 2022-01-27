package com.example.cleanarchitecturesolution.core.di

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import com.example.cleanarchitecturesolution.MainActivity
import org.koin.dsl.module

private const val SPAN_COUNT = 2

val appModule = module {
    //TODO(inject in activity or fragment scope)
    scope<MainActivity> {
        scoped { LinearLayoutManager(get<Context>()) }

        scoped { GridLayoutManager(get<Context>(), SPAN_COUNT) }

        scoped { DividerItemDecoration(get<Context>(), VERTICAL) }
    }
}