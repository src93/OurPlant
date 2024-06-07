package com.cursokotlin.ourplants.core.di

import com.cursokotlin.ourplants.core.HeaderInterceptor
import com.cursokotlin.ourplants.core.exception.ResultCallAdapterFactory
import com.cursokotlin.ourplants.requestnewplan.data.network.clients.UnplashApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(getClient())
            .build()
    }

    @Singleton
    @Provides
    fun providerUnplashApiClient(retrofit: Retrofit): UnplashApiClient {
        return retrofit.create(UnplashApiClient::class.java)
    }

    private fun getClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HeaderInterceptor())
        .build()

}