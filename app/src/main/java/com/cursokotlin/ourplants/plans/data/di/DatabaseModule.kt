package com.cursokotlin.ourplants.plans.data.di

import android.content.Context
import androidx.room.Room
import com.cursokotlin.ourplants.plans.data.PlanDao
import com.cursokotlin.ourplants.plans.data.PlanDatabase
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
    fun provideActivityDatabase(@ApplicationContext appContext: Context): PlanDatabase {
        return Room.databaseBuilder(appContext, PlanDatabase::class.java, "PlanDatabase").build()
    }

    @Provides
    fun providePlanDao(planDatabase: PlanDatabase): PlanDao {
        return planDatabase.planDao()
    }
}