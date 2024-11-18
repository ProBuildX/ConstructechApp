package com.probuildx.constructechapp.views.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.User
import com.probuildx.constructechapp.viewmodels.UsersViewModel

@Composable
fun UserProfileScreen(navController: NavController, userId: Int, usersVm: UsersViewModel = viewModel()) {
    val user by usersVm.user.collectAsState()
    val isLoading by usersVm.isLoading.collectAsState()
    val errorMessage by usersVm.errorMessage.collectAsState()

    LaunchedEffect(userId) { usersVm.getById(userId) }

    when {
        (isLoading || user == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            UserProfile(navController, user!!)

        }
    }
}

@Composable
fun UserProfile(navController: NavController, user: User, usersVm: UsersViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Name: ${user.name} ")
        Text(text = "Email: ${user.email} ")
        Text(text = "Password: ${user.password} ")

        Button(
            onClick = { navController.navigate("sign-in") }
            ) {
            Text(text = "Log Out", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}