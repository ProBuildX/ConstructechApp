package com.probuildx.constructechapp.views.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import com.probuildx.constructechapp.entities.Task
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.TasksViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@ExperimentalMaterial3Api
@Composable
fun TasksScreen(
    navController: NavController,
    projectId: Int,
    projectsVm: ProjectsViewModel = viewModel(),
    tasksVm: TasksViewModel = viewModel()
) {

    val project by projectsVm.project.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    val tasks by tasksVm.tasks.collectAsState()
    val isLoadingW by tasksVm.isLoading.collectAsState()
    val errorMessageW by tasksVm.errorMessage.collectAsState()


    LaunchedEffect(projectId) {
        projectsVm.getById(projectId)
        tasksVm.getByProject(projectId)
    }

    when {
        (isLoadingW || isLoadingP || project == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            Tasks(navController, project!!, tasks)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Tasks(navController: NavController, project: Project, tasks: List<Task>) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tasks") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Project"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        },
        bottomBar = { BottomNavigationBar(navController, project) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Lista de tareas
                if (tasks.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No tasks available",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxHeight(0.9f)
                            .padding(bottom = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(tasks, key = { it.id!! }) { task ->
                            TaskCard(navController, task)
                        }
                    }
                }

                // Botón para agregar una nueva tarea
                Button(
                    onClick = { navController.navigate("new-task/${project.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text(text = "NEW TASK", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun TaskCard(navController: NavController, task: Task) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("task-profile/${task.id}") },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Título de la tarea
            Column {
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.description ?: "No description provided",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }

            // Fecha de inicio
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "Start Date",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF666666)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = task.startDate,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
    }
}
