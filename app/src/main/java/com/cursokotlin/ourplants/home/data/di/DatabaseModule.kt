package com.cursokotlin.ourplants.home.data.di

import android.content.Context
import androidx.room.Room
import com.cursokotlin.ourplants.home.data.KarmaPointDao
import com.cursokotlin.ourplants.home.data.KarmaPointDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideKarmaPointDatabase(@ApplicationContext appContext: Context): KarmaPointDatabase {
        return Room.databaseBuilder(appContext, KarmaPointDatabase::class.java, "KarmaPointDatabase").build()
    }

    @Provides
    fun provideKarmaPointDao(karmaPointDatabase: KarmaPointDatabase): KarmaPointDao {
        return karmaPointDatabase.karmaPointDao()
    }
}