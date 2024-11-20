package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
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
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.UsersViewModel
import com.probuildx.constructechapp.viewmodels.WorkersViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar
import com.probuildx.constructechapp.views.users.UserDashboard

@ExperimentalMaterial3Api
@Composable
fun WorkersScreen(
    navController: NavController,
    projectId: Int,
    projectsVm: ProjectsViewModel = viewModel(),
    workersVm: WorkersViewModel = viewModel()
) {

    val project by projectsVm.project.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    val workers by workersVm.workers.collectAsState()
    val isLoadingW by workersVm.isLoading.collectAsState()
    val errorMessageW by workersVm.errorMessage.collectAsState()


    LaunchedEffect(projectId) {
        projectsVm.getById(projectId)
        workersVm.getByProject(projectId)
    }

    when {
        (isLoadingW || isLoadingP || project == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            Workers(navController, project!!, workers)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Workers(navController: NavController, project: Project, workers: List<Worker>) {
    Scaffold(
        topBar = { StaffTopBar(navController, project.id!!, 1) },
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
                    text = "Workers",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(workers, key = { it.id!! }) { worker ->
                        WorkerCard(navController = navController, worker = worker)
                    }
                }

                Button(
                    onClick = { navController.navigate("new-worker/${project.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Add New Worker",
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
fun WorkerCard(navController: NavController, worker: Worker) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("worker-profile/${worker.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon or Placeholder
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Worker Icon",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${worker.name} ${worker.lastName}",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Role: ${worker.role}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }

            IconButton(onClick = { navController.navigate("worker-profile/${worker.id}") }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to Worker Profile",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}
