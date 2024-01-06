package com.cursokotlin.ourplants.login.domain

import com.cursokotlin.ourplants.login.data.UserEntity
import com.cursokotlin.ourplants.login.data.UserRepository
import javax.inject.Inject

class GetUsers @Inject constructor(private val userRepository: UserRepository) {
    operator fun invoke(): List<UserEntity> {
        return userRepository.getUsers()
    }
}