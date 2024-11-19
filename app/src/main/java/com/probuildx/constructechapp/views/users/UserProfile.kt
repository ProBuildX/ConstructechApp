package com.probuildx.constructechapp.views.users

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "User Profile",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            UserDetailCard(label = "Name", value = user.name)
            UserDetailCard(label = "Email", value = user.email)
            UserDetailCard(label = "Password", value = user.password)
        }

        Button(
            onClick = { navController.navigate("sign-in") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
        ) {
            Text(text = "Log Out", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
fun UserDetailCard(label: String, value: String) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = label,
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = value,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
