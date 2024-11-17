package com.probuildx.constructechapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel

@Composable
fun SignInScreen(navController: NavController, projectsVm: ProjectsViewModel = viewModel()) {

//    val project by projectsVm.project.collectAsState()
//    val isLoading by projectsVm.isLoading.collectAsState()
//    val errorMessage by projectsVm.errorMessage.collectAsState()
//
//    LaunchedEffect(projectId) { projectsVm.getById(projectId) }
//
//    when {
//        isLoading -> CircularProgressIndicator()
//        project == null -> CircularProgressIndicator()
//        errorMessage != null -> Text("$errorMessage")
//
//        else -> { ProjectDashboard(navController, project!!) }
//    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
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
                navController.navigate("projects")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Sign In")
        }
    }

}