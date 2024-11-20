package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.DropdownMenuItem
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
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@ExperimentalMaterial3Api
@Composable
fun TeamProfileScreen(
    navController: NavController,
    teamId: Int,
    teamsVm: TeamsViewModel = viewModel(),
    workerVm: WorkersViewModel = viewModel()
) {
    val team by teamsVm.team.collectAsState()
    val isLoadingT by teamsVm.isLoading.collectAsState()
    val errorMessageT by teamsVm.errorMessage.collectAsState()

    val workers by workerVm.workers.collectAsState()
    val isLoadingW by teamsVm.isLoading.collectAsState()
    val errorMessageW by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(teamId) {
        teamsVm.getById(teamId)
        workerVm.getByTeam(teamId)
    }

    when {
        (isLoadingT || isLoadingW || team == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageT != null -> Text("$errorMessageT", modifier = Modifier.fillMaxSize())
        else -> {

            TeamProfile(navController, team!!, workers)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun TeamProfile(
    navController: NavController,
    team: Team,
    workers: List<Worker>,
    teamsVm: TeamsViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Team Profile") },
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
                // Team Details Card
                Card(
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FA)),
                    elevation = CardDefaults.cardElevation(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = team.name,
                            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = "Workers:",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF666666)
                        )

                        // Worker List
                        workers.forEach { worker ->
                            Text(
                                text = "- ${worker.name} ${worker.lastName}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Black
                            )
                        }
                    }
                }

                // Delete Button
                Button(
                    onClick = {
                        teamsVm.delete(team.id!!)
                        navController.navigate("teams/${team.projectId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF6D6D))
                ) {
                    Text(
                        text = "Delete Team",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    )
}
