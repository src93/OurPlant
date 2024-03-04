package com.cursokotlin.ourplants.login.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * from UserEntity")
    fun getUsers(): Flow<List<UserEntity>>

    @Insert
    suspend fun addUser(userEntity: UserEntity)

    @Query("SELECT * FROM UserEntity WHERE username IN (:username) AND password IN (:password)")
    suspend fun getUser(username: String, password: String): UserEntity?
}