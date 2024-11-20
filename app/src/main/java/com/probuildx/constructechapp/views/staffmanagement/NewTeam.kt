package com.probuildx.constructechapp.views.staffmanagement

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Team
import com.probuildx.constructechapp.viewmodels.TeamsViewModel

@Composable
fun NewTeamScreen(navController: NavController, projectId: Int, teamsVm: TeamsViewModel = viewModel()) {

    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") }
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){

        formFields.forEach { (label, state) ->
            TextField(
                value = state.value,
                onValueChange = { state.value = it },
                label = { Text(label) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Button(
            onClick = {
                val newTeam = Team(
                    name = formFields["Name"]?.value ?: "",
                    projectId = projectId
                )

                teamsVm.create(newTeam)

                navController.navigate("teams/${projectId}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }
}