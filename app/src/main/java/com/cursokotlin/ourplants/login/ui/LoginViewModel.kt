package com.cursokotlin.ourplants.login.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cursokotlin.ourplants.login.domain.GetUsers
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val getUsers: GetUsers) : ViewModel() {
    private val _username = MutableLiveData<String>()
    val username = _username
    private val _password = MutableLiveData<String>()
    val password = _password

    fun checkDataLogin(onCompleteLogin: () -> Unit) {
        val validUsernames = getUsers()
        validUsernames.any { it.username == _username.value && it.password == _password.value }
        val canDoLogin = validUsernames.any { it.username == _username.value && it.password == _password.value }
        Log.i("sergio", "$canDoLogin")
        if (canDoLogin) onCompleteLogin()
    }

    fun handleOnValueChangeUsername(username: String) {
        _username.value = username
    }

    fun handleOnValueChangePassword(password: String) {
        _password.value = password
    }
}