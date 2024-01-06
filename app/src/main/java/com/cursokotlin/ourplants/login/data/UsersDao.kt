package com.cursokotlin.ourplants.login.data

interface UsersDao {
    fun getUsers(): List<UserEntity>
}