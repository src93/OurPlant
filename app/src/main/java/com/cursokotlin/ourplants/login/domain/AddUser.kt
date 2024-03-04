package com.cursokotlin.ourplants.login.domain

import com.cursokotlin.ourplants.login.data.UserRepository
import com.cursokotlin.ourplants.login.ui.model.UserModel
import javax.inject.Inject

class AddUser @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(userModel: UserModel) {
        userRepository.add(userModel = userModel)
    }
}