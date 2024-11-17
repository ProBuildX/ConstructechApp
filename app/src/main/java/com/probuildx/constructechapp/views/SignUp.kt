package com.probuildx.constructechapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.User
import com.probuildx.constructechapp.viewmodels.UsersViewModel

@Composable
fun SignUpScreen(navController: NavController, usersVm: UsersViewModel = viewModel()) {

    val user by usersVm.user.collectAsState()
    val isLoading by usersVm.isLoading.collectAsState()
    val errorMessage by usersVm.errorMessage.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                usersVm.getByEmail(email)

                val newUser = User(
                    name = name,
                    email = email,
                    password = password
                )

                when {
                    isLoading -> println("loading")
                    errorMessage != null -> println("$errorMessage")
                    user != null -> println("email already registered")
                    else -> {
                        usersVm.create(newUser)
                        navController.navigate("sign-in")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SIGN UP")
        }

        Button(
            onClick = { navController.navigate("sign-up")}
        ) {
            Text("sign in")
        }
    }

}