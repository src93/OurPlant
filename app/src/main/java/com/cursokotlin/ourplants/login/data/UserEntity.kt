package com.cursokotlin.ourplants.login.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserEntity(@PrimaryKey val username: String, val password: String)
