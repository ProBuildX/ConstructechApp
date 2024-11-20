package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.WorkersViewModel
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.resourcemanagement.ResourceManagement
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@Composable
fun StaffManagementScreen(navController: NavController, projectId: Int, projectsVm: ProjectsViewModel = viewModel()) {
    val project by projectsVm.project.collectAsState()
    val isLoading by projectsVm.isLoading.collectAsState()
    val errorMessage by projectsVm.errorMessage.collectAsState()

    LaunchedEffect(projectId) { projectsVm.getById(projectId) }

    when {
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        project == null -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            StaffManagementScreen2(navController, project!!)

        }
    }

}

@Composable
fun StaffManagementScreen2(
    navController: NavController,
    project: Project,
    workersVm: WorkersViewModel = viewModel(),
    teamsVm: TeamsViewModel = viewModel()
) {
    val workers by workersVm.workers.collectAsState()
    val isLoadingM by workersVm.isLoading.collectAsState()
    val errorMessageM by workersVm.errorMessage.collectAsState()

    val teams by teamsVm.teams.collectAsState()
    val isLoadingH by teamsVm.isLoading.collectAsState()
    val errorMessageH by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(project.id) {
        workersVm.getByProject(project.id!!)
        teamsVm.getByProject(project.id)
    }

    when {
        (isLoadingM || isLoadingH) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageM != null -> Text("$errorMessageM", modifier = Modifier.fillMaxSize())
        errorMessageH != null -> Text("$errorMessageH", modifier = Modifier.fillMaxSize())
        else -> {

            StaffManagement(navController, project, workers, teams)

        }
    }

}

@Composable
fun StaffManagement(
    navController: NavController,
    project: Project,
    workers: List<Worker>,
    teams: List<Team>
) {
    val workersCount = workers.size
    val teamsCount = teams.size
    val salariesSum = workers.sumOf { it.salary.toIntOrNull() ?: 0 }

    Scaffold(
        topBar = { StaffTopBar(navController, project.id!!, 0) },
        bottomBar = { BottomNavigationBar(navController, project) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Card para el número de trabajadores
                InfoCard(
                    title = "Number of Workers",
                    value = "$workersCount",
                    color = Color(0xFF64B5F6)
                )

                // Card para el número de equipos
                InfoCard(
                    title = "Number of Teams",
                    value = "$teamsCount",
                    color = Color(0xFFFFB74D)
                )

                // Card para el total de salarios
                InfoCard(
                    title = "Total Salaries",
                    value = "$$salariesSum",
                    color = Color(0xFF81C784)
                )

            }
        }
    }
}

@Composable
fun InfoCard(title: String, value: String, color: Color) {
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Black
            )
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = color
            )
        }
    }
}
