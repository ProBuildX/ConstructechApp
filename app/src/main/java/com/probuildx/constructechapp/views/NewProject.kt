package com.probuildx.constructechapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ViewModel

@Composable
fun NewProjectScreen(navController: NavController, viewModel: ViewModel = viewModel()) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ){
        TextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Address") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = budget,
            onValueChange = { budget = it },
            label = { Text("Budget") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Button(
            onClick = {
                val newProject = Project(title = title, description = description)
                viewModel.create(newProject)

                navController.navigate("projects") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }

}