package com.probuildx.constructechapp.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.ViewModel

@Composable
fun WorkersScreen(navController: NavController, projectId: Int,viewModel: ViewModel = viewModel()) {

    val workers by viewModel.workers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    viewModel.getWorkers(projectId)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ){
        when {
            isLoading -> CircularProgressIndicator()
            errorMessage != null -> Text("Error: $errorMessage")
            else -> {
                LazyColumn {
                    items(workers, key = { it.id!! }) { worker ->
                        WorkerCard(navController = navController, worker = worker)
                    }
                }
            }
        }

        Button(
            onClick = { navController.navigate("new-worker") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEW WORKER")
        }
    }

}


@Composable
fun WorkerCard(navController: NavController, worker: Worker) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                //navController.navigate("project-dashboard/${project.id}")
            },
    ){
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = worker.name)
            Text(text = worker.lastName)
        }
    }
}