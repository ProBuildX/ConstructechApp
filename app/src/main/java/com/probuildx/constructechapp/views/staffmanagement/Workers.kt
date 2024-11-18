package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
                    .padding(16.dp)
            ) {

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(workers, key = { it.id!! }) { worker ->
                        WorkerCard(navController = navController, worker = worker)
                    }
                }

            }
        }
    }


}


@Composable
fun WorkerCard(navController: NavController, worker: Worker) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = worker.name,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = worker.lastName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Row {
                IconButton(onClick = { /*falta implementar logica para editar*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF5C6BC0)
                    )
                }
                IconButton(onClick = { /*falta implementar logica para eliminar*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}