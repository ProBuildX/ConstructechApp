package com.probuildx.constructechapp.views

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
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel

@Composable
fun NewProjectScreen(navController: NavController, userId: Int, projectsVm: ProjectsViewModel = viewModel()) {

    val formFields = mapOf(
        "Title" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Address" to remember { mutableStateOf("") },
        "Date" to remember { mutableStateOf("") },
        "Budget" to remember { mutableStateOf("") }
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
                val newProject = Project(
                    title = formFields["Title"]?.value ?: "",
                    description = formFields["Description"]?.value ?: "",
                    userId = userId
                )

                projectsVm.create(newProject)

                navController.navigate("user-dashboard/${userId}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }

}

