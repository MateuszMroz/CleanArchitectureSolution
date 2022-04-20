package com.example.cleanarchitecturesolution.core.di

import com.example.cleanarchitecturesolution.BuildConfig
import com.example.cleanarchitecturesolution.core.data.remote.RickAndMortyApi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Duration
import java.util.concurrent.TimeUnit.SECONDS

val networkModule = module {

    single { get<Retrofit>().create(RickAndMortyApi::class.java) }

    single {
        Retrofit.Builder()
            .baseUrl("https://www.rickandmortyapi.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }

    single<OkHttpClient> {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .readTimeout(5, SECONDS)
            .writeTimeout(5, SECONDS)
            .build()
    }

    single<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) BODY else NONE
        }
    }
}
