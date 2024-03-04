package com.cursokotlin.ourplants.login.data

import android.util.Log
import com.cursokotlin.ourplants.login.ui.model.UserModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    val getUsers: Flow<List<UserModel>> = userDao.getUsers().map { users ->
        users.map {
            UserModel(username = it.username, password = it.password)
        }
    }

    val getUsersByFlow: Flow<List<UserModel>> = flow {
        while (true) {
            userDao.getUsers().onEach { userEntityList ->
                emit(userEntityList.map { UserModel(username = it.username, password = it.password) })
                delay(5000)
            }
        }
    }

    suspend fun add(userModel: UserModel) {
        userDao.addUser(UserEntity(username = userModel.username, password = userModel.password))
    }

    suspend fun getUser(username: String, password: String): UserModel {
        userDao.getUser(username = username, password = password)?.let {
            return  UserModel(username = it.username, password = it.password)
        } ?: kotlin.run {
            return  UserModel(username = "unknown", password = "unknown")
        }
    }
}