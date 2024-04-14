package com.cursokotlin.ourplants.home.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [KarmaPointEntity::class], version = 1)
abstract class KarmaPointDatabase: RoomDatabase() {
    abstract fun karmaPointDao(): KarmaPointDao
}
