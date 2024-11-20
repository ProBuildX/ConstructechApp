package com.probuildx.constructechapp.views.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.entities.Task
import com.probuildx.constructechapp.viewmodels.TasksViewModel
import com.probuildx.constructechapp.viewmodels.TeamsViewModel

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

@Composable
fun TaskProfile(
    navController: NavController,
    task: Task,
    team: Team,
    tasksVm: TasksViewModel = viewModel(),
) {
    //TODO: mejorar interfaz
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Title: ${task.title} ")
        Text(text = "Description: ${task.description} ")
        Text(text = "Start Date: ${task.startDate} ")
        Text(text = "Assigned Team: ${team.name} ")

        Button(
            onClick = {
                tasksVm.delete(task.id!!)
                navController.navigate("tasks/${task.projectId}")
            }
        ) {
            Text(text = "Delete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}