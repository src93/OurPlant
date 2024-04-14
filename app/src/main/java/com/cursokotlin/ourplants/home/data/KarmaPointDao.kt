package com.cursokotlin.ourplants.home.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface KarmaPointDao {
    @Query("SELECT * FROM KarmaPointEntity")
    fun getKarmaPoints(): Flow<List<KarmaPointEntity>>

    @Query("SELECT * FROM KarmaPointEntity WHERE username IN (:username)")
    suspend fun getUser(username: String): KarmaPointEntity

    @Update
    suspend fun updateKarmaPoints(karmaPointEntity: KarmaPointEntity)
}