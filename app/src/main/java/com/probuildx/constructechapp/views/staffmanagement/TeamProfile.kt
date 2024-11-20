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
import com.probuildx.constructechapp.viewmodels.TeamsViewModel

@Composable
fun TeamProfileScreen(navController: NavController, teamId: Int, teamsVm: TeamsViewModel = viewModel()) {
    val team by teamsVm.team.collectAsState()
    val isLoading by teamsVm.isLoading.collectAsState()
    val errorMessage by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(teamId) { teamsVm.getById(teamId) }

    when {
        (isLoading || team == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            TeamProfile(navController, team!!)

        }
    }
}

@Composable
fun TeamProfile(navController: NavController, team: Team, teamsVm: TeamsViewModel = viewModel()) {
    //TODO: mejorar interfaz
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Name: ${team.name} ")

        Button(
            onClick = {
                teamsVm.delete(team.id!!)
                navController.navigate("teams/${team.projectId}")
            }
        ) {
            Text(text = "Delete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}