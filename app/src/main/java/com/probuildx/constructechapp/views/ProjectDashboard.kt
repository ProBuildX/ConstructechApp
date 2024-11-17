package com.probuildx.constructechapp.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel

@Composable
fun ProjectDashboardScreen(navController: NavController, projectId: Int, viewModel: ProjectsViewModel = viewModel()) {

    val project by viewModel.project.collectAsState()

    //LaunchedEffect(projectId) { viewModel.get(projectId) }
    viewModel.getById(projectId)


    when {
        project == null -> CircularProgressIndicator()

        else -> { ProjectDashboard(navController, project!!) }
    }

//    project?.let { ProjectDashboard(project = it) }
//        ?: run { CircularProgressIndicator() }

}

@Composable
fun ProjectDashboard(navController: NavController, project: Project) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        Text(project.title)

        Button(
            onClick = { navController.navigate("workers/${project.id}") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Workers & Teams")
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Resource Management")
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Tasks")
        }
    }

}