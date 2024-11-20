package com.probuildx.constructechapp.views.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.entities.Task
import com.probuildx.constructechapp.viewmodels.TasksViewModel
import com.probuildx.constructechapp.viewmodels.TeamsViewModel

@ExperimentalMaterial3Api
@Composable
fun TaskProfileScreen(navController: NavController, taskId: Int, tasksVm: TasksViewModel = viewModel()) {
    val task by tasksVm.task.collectAsState()
    val isLoading by tasksVm.isLoading.collectAsState()
    val errorMessage by tasksVm.errorMessage.collectAsState()

    LaunchedEffect(taskId) { tasksVm.getById(taskId) }

    when {
        (isLoading || task == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            TaskProfileScreen2(navController, task!!)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TaskProfileScreen2(navController: NavController, task: Task, teamsVm: TeamsViewModel = viewModel()) {

    val team by teamsVm.team.collectAsState()
    val isLoading by teamsVm.isLoading.collectAsState()
    val errorMessage by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(task.teamId) { teamsVm.getById(task.teamId) }

    when {
        (isLoading || team == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            TaskProfile(navController, task, team!!)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TaskProfile(
    navController: NavController,
    task: Task,
    team: Team,
    tasksVm: TasksViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Task Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Tasks"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Tarjeta con detalles de la tarea
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Title",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = task.description ?: "No description provided",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Start Date",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = task.startDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Assigned Team",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = team.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // Botón para eliminar la tarea
                Button(
                    onClick = {
                        tasksVm.delete(task.id!!)
                        navController.navigate("tasks/${task.projectId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text(text = "DELETE TASK", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}