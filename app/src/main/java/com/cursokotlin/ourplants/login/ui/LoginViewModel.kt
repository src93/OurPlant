package com.cursokotlin.ourplants.login.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.ourplants.login.domain.AddUser
import com.cursokotlin.ourplants.login.domain.GetUser
import com.cursokotlin.ourplants.login.domain.GetUsers
import com.cursokotlin.ourplants.login.ui.model.UserModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUsers: GetUsers,
    private val addUser: AddUser,
    private val getUser: GetUser
) : ViewModel() {
    private val _username = MutableLiveData<String>("")
    val username = _username
    private val _password = MutableLiveData<String>("")
    val password = _password
    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage = _toastMessage.asSharedFlow()

    init {
        viewModelScope.launch {
            val user = getUser("Sergio", "123")
            Log.i("Login", "username: ${user.username}")
        }
    }

    private fun getUsersDB() {
        viewModelScope.launch {
            getUsers().collect() {userModelList ->
                userModelList.forEach() {
                    Log.i("Login", "username: ${it.username}")
                }
            }
        }
    }

    fun checkDataLogin(onCompleteLogin: () -> Unit) {
        Log.i("Login", "entra en el check")
        viewModelScope.launch {
            Log.i("Login", "entra en el launch")
            val user = getUser(username = _username.value!!, password = _password.value!!)
            synchronized(this@LoginViewModel) {
                Log.i("Login", "entra en synchronized ${user.username}")
                if (user.username !== "unknown") {
                    onCompleteLogin()
                } else {
                    Log.i("Login", "entra en el else")
                    viewModelScope.launch {
                        Log.i("Login", "entra en el launch 2")
                        _toastMessage.emit("Usuario o contraseña inválido")
                    }
                }
            }
        }
    }

    fun handleOnValueChangeUsername(username: String) {
        _username.value = username
    }

    fun handleOnValueChangePassword(password: String) {
        _password.value = password
    }

    fun showToast(message: String) {
        viewModelScope.launch {
            _toastMessage.emit("Prueba")
        }
    }
}