@file:OptIn(ExperimentalMaterial3Api::class)

package com.cursokotlin.ourplants.login.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(loginViewModel: LoginViewModel, onCompleteLogin: () -> Unit) {
    val username by loginViewModel.username.observeAsState("")
    val password by loginViewModel.password.observeAsState("")
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 32.dp)
    ) {
        LoginHeader()
        LoginForm(
            modifier = Modifier.align(Alignment.Center),
            username = username,
            password = password,
            loginViewModel = loginViewModel
        )
        LoginButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            loginViewModel = loginViewModel,
            onCompleteLogin = onCompleteLogin
        )
        ShowToast(loginViewModel = loginViewModel)
    }
}

@Composable
fun LoginHeader() {
    Text(
        text = "Our Plans, la app para apuntar tus planes de hoy y de mañana",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun LoginForm(
    modifier: Modifier,
    username: String,
    password: String,
    loginViewModel: LoginViewModel
) {
    Column(modifier = modifier) {
        UsernameField(username = username, loginViewModel = loginViewModel)
        PasswordField(password = password, loginViewModel = loginViewModel)
    }
}

@Composable
fun UsernameField(username: String, loginViewModel: LoginViewModel) {
    OutlinedTextField(
        value = username,
        modifier = Modifier.padding(bottom = 16.dp),
        onValueChange = { loginViewModel.handleOnValueChangeUsername(it) },
        label = { Text(text = "Username") },
        shape = RoundedCornerShape(15.dp),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "Username")
        },
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Color.Blue,
            textColor = Color.Black,
            focusedLeadingIconColor = Color.Blue,
            unfocusedLeadingIconColor = Color.Black,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Black,
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun PasswordField(password: String, loginViewModel: LoginViewModel) {
    OutlinedTextField(
        value = password,
        onValueChange = { loginViewModel.handleOnValueChangePassword(it) },
        label = { Text(text = "Password") },
        shape = RoundedCornerShape(25.dp),
        maxLines = 1,
        singleLine = true,
        leadingIcon = {
            Icon(imageVector = Icons.Default.Lock, contentDescription = "Password")
        },
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        colors = TextFieldDefaults.textFieldColors(
            focusedLabelColor = Color.Blue,
            textColor = Color.Black,
            focusedLeadingIconColor = Color.Blue,
            unfocusedLeadingIconColor = Color.Black,
            focusedIndicatorColor = Color.Blue,
            unfocusedIndicatorColor = Color.Black,
            containerColor = Color.Transparent
        )
    )
}

@Composable
fun LoginButton(
    modifier: Modifier,
    loginViewModel: LoginViewModel,
    onCompleteLogin: () -> Unit
) {
    Button(onClick = { loginViewModel.checkDataLogin(onCompleteLogin) }, modifier = modifier, shape = RoundedCornerShape(5.dp)) {
        Text(text = "Entrar")
    }
}

@Composable
fun ShowToast(loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        loginViewModel.toastMessage.collect { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
