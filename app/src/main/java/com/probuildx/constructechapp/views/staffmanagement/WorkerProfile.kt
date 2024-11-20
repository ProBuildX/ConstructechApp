package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
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
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
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
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> WorkerProfile(navController, worker, null)
        else -> WorkerProfile(navController, worker, team)
    }
}

@ExperimentalMaterial3Api
@Composable
fun WorkerProfile(
    navController: NavController,
    worker: Worker,
    team: Team?,
    workersVm: WorkersViewModel = viewModel(),
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Worker Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Worker Details Card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                    elevation = CardDefaults.cardElevation(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Title Section
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Worker Icon",
                                tint = Color(0xFF4CAF50),
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(
                                text = worker.name + " " + worker.lastName,
                                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                                color = Color(0xFF333333)
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Worker Details
                        DetailRow(label = "DNI", value = worker.dni)
                        DetailRow(label = "Role", value = worker.role)
                        DetailRow(label = "Salary", value = "$${worker.salary}")
                        DetailRow(label = "Team", value = team?.name ?: "Not Assigned")
                    }
                }

                // Delete Button
                Button(
                    onClick = {
                        workersVm.delete(worker.id!!)
                        navController.navigate("workers/${worker.projectId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D6D))
                ) {
                    Text(
                        text = "Delete Worker",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF666666)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Black
        )
    }
}
