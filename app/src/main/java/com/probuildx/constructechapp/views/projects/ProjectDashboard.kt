package com.probuildx.constructechapp.views.projects

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.R
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@Composable
fun ProjectDashboardScreen(navController: NavController, projectId: Int, projectsVm: ProjectsViewModel = viewModel()) {
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
                    ProjectDashboard(navController, project!!)
                }
            }

        }
    }

}

@Composable
fun ProjectDashboard(navController: NavController, project: Project) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Icono superior
            Icon(
                imageVector = Icons.Rounded.Home,
                contentDescription = "Project Icon",
                tint = Color(0xFFFF9800),
                modifier = Modifier
                    .size(100.dp)
                    .padding(bottom = 16.dp)
            )

            // Título del proyecto
            Text(
                text = project.title,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp),
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            // Descripción del proyecto
            Text(
                text = project.description,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = TextAlign.Center
            )

            // Tarjetas con íconos
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Fila superior
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ActionCard(
                        title = "Staff Management",
                        onClick = { navController.navigate("staff-management/${project.id}") },
                        iconResId = R.drawable.ih_staffmanagement
                    )
                    ActionCard(
                        title = "Resource Management",
                        onClick = { navController.navigate("resource-management/${project.id}") },
                        iconResId = R.drawable.ih_resourcemanagent
                    )
                }

                // Fila inferior
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    ActionCard(
                        title = "Tasks",
                        onClick = { navController.navigate("tasks/${project.id}") },
                        iconResId = R.drawable.ih_taskmanagement
                    )
                }
            }
        }
    }
}

@Composable
fun ActionCard(title: String, onClick: () -> Unit, iconResId: Int) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(140.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}