package com.probuildx.constructechapp.views.tasks

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Task
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.TasksViewModel

@ExperimentalMaterial3Api
@Composable
fun NewTaskScreen(
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

            NewTask(navController, projectId, teams)

        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun NewTask(
    navController: NavController,
    projectId: Int,
    teams: List<Team>,
    tasksVm: TasksViewModel = viewModel(),
) {
    // Mapa para los campos del formulario
    val formFields = mapOf(
        "Title" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Start Date" to remember { mutableStateOf("") }
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select a Team") }
    var selectedId by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Create New Task") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
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
                Column {
                    // Generar los campos del formulario
                    formFields.forEach { (label, state) ->
                        OutlinedTextField(
                            value = state.value,
                            onValueChange = { state.value = it },
                            label = { Text(label) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                        )
                    }

                    // Dropdown para seleccionar un equipo
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { expanded = true }
                            .border(
                                width = 1.dp,
                                color = Color.Gray,
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(16.dp)
                    ) {
                        Text(
                            text = selectedOption,
                            color = if (selectedOption == "Select a Team") Color.Gray else Color.Black
                        )
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

                    // Botón de guardar tarea
                    Button(
                        onClick = {
                            if (selectedOption != "Select a Team") {
                                val newTask = Task(
                                    title = formFields["Title"]?.value ?: "",
                                    description = formFields["Description"]?.value ?: "",
                                    startDate = formFields["Start Date"]?.value ?: "",
                                    projectId = projectId,
                                    teamId = selectedId
                                )

                                tasksVm.create(newTask)
                                navController.navigate("tasks/${projectId}")
                            }
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
    }
}

