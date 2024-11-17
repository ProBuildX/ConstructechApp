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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@Composable
fun ProjectsScreen(navController: NavController, projectsVm: ProjectsViewModel = viewModel()) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        ProjectsList(navController = navController)

        Button(
            onClick = { navController.navigate("new-project") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEW PROJECT")
        }
    }

}

@Composable
fun ProjectsList(navController: NavController, userId: Int = 0, projectsVm: ProjectsViewModel = viewModel()) {

    val projects by projectsVm.projects.collectAsState()
    val isLoading by projectsVm.isLoading.collectAsState()
    val errorMessage by projectsVm.errorMessage.collectAsState()

    //LaunchedEffect(Unit) { projectsVm.getByProject(projectId) }

    when {
        isLoading -> CircularProgressIndicator()
        errorMessage != null -> Text("$errorMessage")
        else -> {
            LazyColumn {
                items(projects, key = { it.id!! }) { project ->
                    ProjectCard(navController = navController, project = project)
                }
            }
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