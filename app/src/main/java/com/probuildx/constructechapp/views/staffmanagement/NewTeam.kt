package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.viewmodels.TeamsViewModel

@ExperimentalMaterial3Api
@Composable
fun NewTeamScreen(navController: NavController, projectId: Int, teamsVm: TeamsViewModel = viewModel()) {
    val formFields = mapOf(
        "Team Name" to remember { mutableStateOf("") }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Team") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Teams"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Title
                Text(
                    text = "Add a New Team",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                // Input Field
                formFields.forEach { (label, state) ->
                    TextField(
                        value = state.value,
                        onValueChange = { state.value = it },
                        label = { Text(label) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(containerColor = Color(0xFFF7F7F7))
                    )
                }

                // Save Button
                Button(
                    onClick = {
                        val newTeam = Team(
                            name = formFields["Team Name"]?.value ?: "",
                            projectId = projectId
                        )

                        teamsVm.create(newTeam)
                        navController.navigate("teams/${projectId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("SAVE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
