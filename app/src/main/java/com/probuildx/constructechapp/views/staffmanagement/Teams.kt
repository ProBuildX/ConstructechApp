package com.probuildx.constructechapp.views.staffmanagement

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Face
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@ExperimentalMaterial3Api
@Composable
fun TeamsScreen(
    navController: NavController,
    projectId: Int,
    projectsVm: ProjectsViewModel = viewModel(),
    teamsVm: TeamsViewModel = viewModel()
) {

    val project by projectsVm.project.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    val teams by teamsVm.teams.collectAsState()
    val isLoadingW by teamsVm.isLoading.collectAsState()
    val errorMessageW by teamsVm.errorMessage.collectAsState()


    LaunchedEffect(projectId) {
        projectsVm.getById(projectId)
        teamsVm.getByProject(projectId)
    }

    when {
        (isLoadingW || isLoadingP || project == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            Teams(navController, project!!, teams)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun Teams(navController: NavController, project: Project, teams: List<Team>) {
    Scaffold(
        topBar = { StaffTopBar(navController, project.id!!, 2) },
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
                    text = "Teams",
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
                    items(teams, key = { it.id!! }) { team ->
                        TeamCard(navController = navController, team = team)
                    }
                }

                Button(
                    onClick = { navController.navigate("new-team/${project.id}") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                ) {
                    Text(
                        text = "Add New Team",
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
fun TeamCard(navController: NavController, team: Team) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navController.navigate("team-profile/${team.id}") },
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
                imageVector = Icons.Default.Face,
                contentDescription = "Team Icon",
                tint = Color(0xFF4CAF50),
                modifier = Modifier.size(48.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = team.name,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Team Details",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF666666)
                )
            }

            IconButton(onClick = { navController.navigate("team-profile/${team.id}") }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Go to Team Profile",
                    tint = Color(0xFF4CAF50)
                )
            }
        }
    }
}
