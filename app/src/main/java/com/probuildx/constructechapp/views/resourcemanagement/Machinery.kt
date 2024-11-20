package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Machine
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.MachineryViewModel
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@ExperimentalMaterial3Api
@Composable
fun MachineryScreen(
    navController: NavController,
    projectId: Int,
    projectsVm: ProjectsViewModel = viewModel(),
    machineryVm: MachineryViewModel = viewModel()
) {

    val project by projectsVm.project.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    val machines by machineryVm.machines.collectAsState()
    val isLoadingW by machineryVm.isLoading.collectAsState()
    val errorMessageW by machineryVm.errorMessage.collectAsState()


    LaunchedEffect(projectId) {
        projectsVm.getById(projectId)
        machineryVm.getByProject(projectId)
    }

    when {
        (isLoadingW || isLoadingP || project == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            Machinery(navController, project!!, machines)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Machinery(navController: NavController, project: Project, machines: List<Machine>) {
    Scaffold(
        topBar = { ResourceTopBar(navController, project.id!!, 2) },
        bottomBar = { BottomNavigationBar(navController, project) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Machinery",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = Color(0xFF333333)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(machines, key = { it.id!! }) { machine ->
                        MachineCard(navController = navController, machine = machine)
                    }
                }

                Button(
                    onClick = { navController.navigate("new-machine/${project.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Add New Machine",
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        color = Color.White
                    )
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun MachineCard(navController: NavController, machine: Machine) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("machine-profile/${machine.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = machine.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Start Date: ${machine.startDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }

            IconButton(onClick = { navController.navigate("machine-profile/${machine.id}") }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to Machine Profile",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}
