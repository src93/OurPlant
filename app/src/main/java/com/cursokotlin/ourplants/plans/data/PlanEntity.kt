package com.cursokotlin.ourplants.plans.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlanEntity (@PrimaryKey(autoGenerate = true) val id:Int = 0, val plan:String, val image:String, val description: String, val stars: Int)