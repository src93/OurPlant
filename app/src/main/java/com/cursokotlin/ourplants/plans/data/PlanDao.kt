package com.cursokotlin.ourplants.plans.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PlanDao {
    @Query("SELECT * FROM PlanEntity")
    fun getActivities(): Flow<List<PlanEntity>>

    @Insert
    suspend fun addPlan(planEntity: PlanEntity)
}