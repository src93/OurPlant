package com.cursokotlin.ourplants.login.domain

import com.cursokotlin.ourplants.login.data.UserRepository
import com.cursokotlin.ourplants.login.ui.model.UserModel
import javax.inject.Inject

class GetUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(username: String, password: String): UserModel {
        return userRepository.getUser(username, password)
    }
}