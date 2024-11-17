package com.probuildx.constructechapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.probuildx.constructechapp.viewmodels.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.probuildx.constructechapp.entities.Project

@Composable
fun ProjectsScreen(navController: NavController, viewModel: ViewModel = viewModel()) {

    val projects by viewModel.posts.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text("Error: $errorMessage")
            else -> {
                LazyColumn {
                    items(projects, key = { it.id!! }) { project ->
                        ProjectCard(navController = navController, project = project)
                    }
                }
            }
        }

        Button(
            onClick = { navController.navigate("new-project") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEW PROJECT")
        }
    }

}


@Composable
fun ProjectCard(navController: NavController, project: Project) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate("project-dashboard/${project.id}")
            },
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Name: ${project.title}")
            Text(text = project.description)
        }
    }
}