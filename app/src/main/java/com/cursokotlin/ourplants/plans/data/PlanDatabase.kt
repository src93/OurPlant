package com.cursokotlin.ourplants.plans.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PlanEntity::class], version = 1)
abstract class PlanDatabase: RoomDatabase() {
    abstract fun planDao(): PlanDao
}