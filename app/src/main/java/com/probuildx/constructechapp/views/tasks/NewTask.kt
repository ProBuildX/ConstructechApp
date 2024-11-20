package com.probuildx.constructechapp.views.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Task
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.viewmodels.TeamsViewModel
import com.probuildx.constructechapp.viewmodels.TasksViewModel

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

@Composable
fun NewTask(
    navController: NavController,
    projectId: Int,
    teams: List<Team>,
    tasksVm: TasksViewModel = viewModel(),
) {

    //TODO: este es un mapa, como un dictionario de python
    val formFields = mapOf(
        "Title" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Start Date" to remember { mutableStateOf("") }
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
                if (selectedOption != "Select an option"){
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
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }
}
