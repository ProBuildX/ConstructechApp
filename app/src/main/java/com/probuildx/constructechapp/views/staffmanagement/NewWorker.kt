package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

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

@Composable
fun NewWorker(
    navController: NavController,
    projectId: Int,
    teams: List<Team>,
    workersVm: WorkersViewModel = viewModel(),
) {

    //TODO: este es un mapa, como un dictionario de python
    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") },
        "Last Name" to remember { mutableStateOf("") },
        "DNI" to remember { mutableStateOf("") },
        "Role" to remember { mutableStateOf("") },
        "Salary" to remember { mutableStateOf("") }
    )

    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("Select an option") }
    var selectedId by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){

        //TODO: este for loop usa el mapa para generar los TextFields
        //TODO: en NewWorker lo cambiaste, pero se hace un codigo muy largo
        //TODO: trabaja de esta forma es mas ordenado
        //TODO: si no se entiende me preguntas
        formFields.forEach { (label, state) ->
            TextField(
                value = state.value,
                onValueChange = { state.value = it },
                label = { Text(label) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }


        Box(modifier = Modifier.wrapContentSize()) {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .clickable { expanded = true }
                    .padding(16.dp)
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }
}
