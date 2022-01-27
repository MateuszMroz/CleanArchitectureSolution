package com.example.cleanarchitecturesolution.core.di

import com.example.cleanarchitecturesolution.BuildConfig
import com.example.cleanarchitecturesolution.features.data.remote.RickAndMortyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single { get<Retrofit>().create(RickAndMortyApi::class.java) }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
    }

    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        }
    }
}
