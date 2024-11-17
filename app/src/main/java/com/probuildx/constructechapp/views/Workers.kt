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
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@Composable
fun WorkersScreen(navController: NavController, projectId: Int) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ){
        WorkersList(navController = navController, projectId = projectId)

        Button(
            onClick = { navController.navigate("new-worker") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("NEW WORKER")
        }
    }

}

@Composable
fun WorkersList(navController: NavController, projectId: Int, workersVM: WorkersViewModel = viewModel()) {

    val workers by workersVM.workers.collectAsState()
    val isLoading by workersVM.isLoading.collectAsState()
    val errorMessage by workersVM.errorMessage.collectAsState()

    workersVM.getByProject(projectId)

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