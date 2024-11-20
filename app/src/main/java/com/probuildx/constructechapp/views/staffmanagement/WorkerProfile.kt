package com.probuildx.constructechapp.views.staffmanagement

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
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@Composable
fun WorkerProfileScreen(navController: NavController, workerId: Int, workersVm: WorkersViewModel = viewModel()) {
    val worker by workersVm.worker.collectAsState()
    val isLoading by workersVm.isLoading.collectAsState()
    val errorMessage by workersVm.errorMessage.collectAsState()

    LaunchedEffect(workerId) { workersVm.getById(workerId) }

    when {
        (isLoading || worker == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            WorkerProfileScreen2(navController, worker!!)

        }
    }
}

@Composable
fun WorkerProfileScreen2(
    navController: NavController,
    worker: Worker,
    teamsVm: TeamsViewModel = viewModel()
) {
    val team by teamsVm.team.collectAsState()
    val isLoading by teamsVm.isLoading.collectAsState()
    val errorMessage by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(worker.teamId) { teamsVm.getById(worker.teamId) }

    when {
        (isLoading) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> WorkerProfile(navController, worker, null)
        else -> {

            WorkerProfile(navController, worker, team)

        }
    }
}

@Composable
fun WorkerProfile(
    navController: NavController,
    worker: Worker,
    team: Team?,
    workersVm: WorkersViewModel = viewModel(),
) {
    //TODO: mejorar interfaz
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Name: ${worker.name} ")
        Text(text = "Last Name: ${worker.lastName} ")
        Text(text = "DNI: ${worker.dni} ")
        Text(text = "Role: ${worker.role} ")
        Text(text = "Salary: ${worker.salary} ")
        if (team == null) { Text(text = "Team: sin asignar") }
        else { Text(text = "Team: ${team.name} ") }


        Button(
            onClick = {
                workersVm.delete(worker.id!!)
                navController.navigate("workers/${worker.projectId}")
            }
        ) {
            Text(text = "Delete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}