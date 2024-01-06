package com.cursokotlin.ourplants.login.data

import javax.inject.Inject

class UserRepository @Inject constructor(): UsersDao {
    override fun getUsers(): List<UserEntity> {
        val sergio = UserEntity(username = "Sergio", password = "nutriasninja")
        val andrea = UserEntity(username = "Andrea", password = "nutriasninja")
        return listOf(sergio, andrea)
    }
}