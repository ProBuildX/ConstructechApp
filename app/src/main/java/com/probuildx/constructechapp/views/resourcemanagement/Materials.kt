package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Material
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.MaterialsViewModel
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.views.shared.BottomNavigationBar

@Composable
fun MaterialsScreen(
    navController: NavController,
    projectId: Int,
    projectsVm: ProjectsViewModel = viewModel(),
    materialsVm: MaterialsViewModel = viewModel()
) {

    val project by projectsVm.project.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    val materials by materialsVm.materials.collectAsState()
    val isLoadingW by materialsVm.isLoading.collectAsState()
    val errorMessageW by materialsVm.errorMessage.collectAsState()


    LaunchedEffect(projectId) {
        projectsVm.getById(projectId)
        materialsVm.getByProject(projectId)
    }

    when {
        (isLoadingW || isLoadingP || project == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageW != null -> Text("$errorMessageW", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            Materials(navController, project!!, materials)

        }
    }
}

@Composable
fun Materials(navController: NavController, project: Project, materials: List<Material>) {
    //TODO: mejorar interfaz
    Scaffold(
        topBar = { ResourceTopBar(navController, project.id!!, 1) },
        bottomBar = { BottomNavigationBar(navController, project) }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {

                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    items(materials, key = { it.id!! }) { material ->
                        MaterialCard(navController = navController, material = material)
                    }
                }

                Button(
                    onClick = { navController.navigate("new-material/${project.id}") },
                ) {
                    Text(text = "New Material")
                }

            }
        }
    }


}


@Composable
fun MaterialCard(navController: NavController, material: Material) {
    //TODO: mejorar interfaz
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("material-profile/${material.id}") },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = material.name)

            Column {
                Text(
                    text = "Amount",
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = material.amount,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }

        }
    }
}