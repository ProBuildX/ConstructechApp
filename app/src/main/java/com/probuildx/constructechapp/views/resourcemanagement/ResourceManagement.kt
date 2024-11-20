package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import com.probuildx.constructechapp.entities.Machine
import com.probuildx.constructechapp.entities.Material
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.MachineryViewModel
import com.probuildx.constructechapp.viewmodels.MaterialsViewModel
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar
import com.probuildx.constructechapp.views.staffmanagement.TeamProfile

@Composable
fun ResourceManagementScreen(navController: NavController, projectId: Int, projectsVm: ProjectsViewModel = viewModel()) {
    val project by projectsVm.project.collectAsState()
    val isLoading by projectsVm.isLoading.collectAsState()
    val errorMessage by projectsVm.errorMessage.collectAsState()

    LaunchedEffect(projectId) { projectsVm.getById(projectId) }

    when {
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        project == null -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            ResourceManagementScreen2(navController, project!!)

        }
    }

}

@Composable
fun ResourceManagementScreen2(
    navController: NavController,
    project: Project,
    materialsVm: MaterialsViewModel = viewModel(),
    machineryVm: MachineryViewModel = viewModel()
) {
    val materials by materialsVm.materials.collectAsState()
    val isLoadingM by materialsVm.isLoading.collectAsState()
    val errorMessageM by materialsVm.errorMessage.collectAsState()

    val machines by machineryVm.machines.collectAsState()
    val isLoadingH by machineryVm.isLoading.collectAsState()
    val errorMessageH by machineryVm.errorMessage.collectAsState()

    LaunchedEffect(project.id) {
        materialsVm.getByProject(project.id!!)
        machineryVm.getByProject(project.id)
    }

    when {
        (isLoadingM || isLoadingH) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageM != null -> Text("$errorMessageM", modifier = Modifier.fillMaxSize())
        errorMessageH != null -> Text("$errorMessageH", modifier = Modifier.fillMaxSize())
        else -> {

            ResourceManagement(navController, project, materials, machines)

        }
    }

}

@Composable
fun ResourceManagement(
    navController: NavController,
    project: Project,
    materials: List<Material>,
    machines: List<Machine>
) {
    val materialsSum = materials.sumOf { it.totalCost?.toIntOrNull() ?: 0 }
    val machinerySum = machines.sumOf { it.totalCost?.toIntOrNull() ?: 0 }
    val totalCost = materialsSum + machinerySum

    Scaffold(
        topBar = { ResourceTopBar(navController, project.id!!, 0) },
        bottomBar = { BottomNavigationBar(navController, project) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Material Costs Card
                ResourceCard(
                    title = "Total Cost of Materials",
                    cost = materialsSum,
                    color = Color(0xFF64B5F6)
                )

                // Machinery Costs Card
                ResourceCard(
                    title = "Total Cost of Machinery",
                    cost = machinerySum,
                    color = Color(0xFFFFB74D)
                )

                // Total Costs Card
                ResourceCard(
                    title = "Total Cost",
                    cost = totalCost,
                    color = Color(0xFF81C784)
                )

            }
        }
    }
}

@Composable
fun ResourceCard(title: String, cost: Int, color: Color) {
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
                text = "$$cost",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = color
            )
        }
    }
}
