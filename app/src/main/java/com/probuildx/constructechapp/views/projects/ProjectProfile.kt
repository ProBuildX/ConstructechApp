package com.probuildx.constructechapp.views.projects

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
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
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@ExperimentalMaterial3Api
@Composable
fun ProjectProfileScreen(navController: NavController, projectId: Int, projectsVm: ProjectsViewModel = viewModel()) {
    val project by projectsVm.project.collectAsState()
    val isLoading by projectsVm.isLoading.collectAsState()
    val errorMessage by projectsVm.errorMessage.collectAsState()

    LaunchedEffect(projectId) { projectsVm.getById(projectId) }

    when {
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        project == null -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            Scaffold(
                bottomBar = { BottomNavigationBar(navController, project!!) }
            ) {
                paddingValues ->
                Box(modifier = Modifier.padding(paddingValues)) {
                    ProjectProfile(navController, project!!)
                }
            }

        }
    }

}

@ExperimentalMaterial3Api
@Composable
fun ProjectProfile(navController: NavController, project: Project, projectsVm: ProjectsViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Project Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Project Details Card
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F1F1))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Project Title
                        Text(
                            text = project.title,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF222222)
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp), color = Color(0xFFE0E0E0))
                        // Details Section
                        DetailRow(label = "Description", value = project.description)
                        DetailRow(label = "Address", value = project.address)
                        DetailRow(label = "Date", value = project.date)
                        DetailRow(label = "Budget", value = "$${project.budget}")
                    }
                }

                // Delete Button
                Button(
                    onClick = {
                        projectsVm.delete(project.id!!)
                        navController.navigate("user-dashboard/${project.userId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                ) {
                    Text(text = "Delete Project", fontWeight = FontWeight.Bold, fontSize = 16.sp, color = Color.White)
                }
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = label,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = value,
            fontSize = 16.sp,
            color = Color.Black
        )
    }
}