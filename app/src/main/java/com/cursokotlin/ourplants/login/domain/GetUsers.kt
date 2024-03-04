package com.cursokotlin.ourplants.login.domain

import com.cursokotlin.ourplants.login.data.UserEntity
import com.cursokotlin.ourplants.login.data.UserRepository
import com.cursokotlin.ourplants.login.ui.model.UserModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUsers @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): Flow<List<UserModel>> {
        return userRepository.getUsers
    }
}