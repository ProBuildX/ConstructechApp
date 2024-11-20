package com.probuildx.constructechapp.views.staffmanagement


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun NewWorkerScreen(
    navController: NavController,
    projectId: Int,
    teamsVm: TeamsViewModel = viewModel()
) {
    val teams by teamsVm.teams.collectAsState()
    val isLoading by teamsVm.isLoading.collectAsState()
    val errorMessage by teamsVm.errorMessage.collectAsState()

    LaunchedEffect(projectId) { teamsVm.getByProject(projectId) }

    when {
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            NewWorker(navController, projectId, teams)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewWorker(
    navController: NavController,
    projectId: Int,
    teams: List<Team>,
    workersVm: WorkersViewModel = viewModel(),
) {
    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") },
        "Last Name" to remember { mutableStateOf("") },
        "DNI" to remember { mutableStateOf("") },
        "Role" to remember { mutableStateOf("") },
        "Salary" to remember { mutableStateOf("") }
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a team") }
    var selectedId by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Worker") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Enter Worker Details",
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = Color(0xFF333333)
            )

            // Input fields for worker details
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

            // Team selection dropdown
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .wrapContentSize(Alignment.TopStart)
            ) {
                OutlinedButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth(),
                    shape = MaterialTheme.shapes.medium
                ) {
                    Text(
                        text = selectedOption,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (selectedOption == "Select a team") Color.Gray else Color.Black
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    teams.forEach { team ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOption = team.name
                                selectedId = team.id!!
                                expanded = false
                            },
                            text = { Text(team.name) }
                        )
                    }
                }
            }

            // Save button
            Button(
                onClick = {
                    val newWorker = Worker(
                        name = formFields["Name"]?.value ?: "",
                        lastName = formFields["Last Name"]?.value ?: "",
                        dni = formFields["DNI"]?.value ?: "",
                        role = formFields["Role"]?.value ?: "",
                        salary = formFields["Salary"]?.value ?: "",
                        projectId = projectId,
                        teamId = selectedId,
                    )

                    workersVm.create(newWorker)
                    navController.navigate("workers/${projectId}")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
            ) {
                Text("SAVE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}
