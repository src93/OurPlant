package com.cursokotlin.ourplants.home.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class KarmaPointEntity(@PrimaryKey val username: String, val karmaPoints: Int)
