package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Material
import com.probuildx.constructechapp.viewmodels.MaterialsViewModel


@Composable
fun MaterialProfileScreen(navController: NavController, materialId: Int, materialsVm: MaterialsViewModel = viewModel()) {
    val material by materialsVm.material.collectAsState()
    val isLoading by materialsVm.isLoading.collectAsState()
    val errorMessage by materialsVm.errorMessage.collectAsState()

    LaunchedEffect(materialId) { materialsVm.getById(materialId) }

    when {
        (isLoading || material == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            MaterialProfile(navController, material!!)

        }
    }
}

@Composable
fun MaterialProfile(navController: NavController, material: Material, materialsVm: MaterialsViewModel = viewModel()) {
    //TODO: mejorar interfaz
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Name: ${material.name} ")
        Text(text = "Description: ${material.description} ")
        Text(text = "Amount: ${material.amount} ")
        Text(text = "Reception Date: ${material.receptionDate} ")
        Text(text = "Total Cost: ${material.totalCost} ")

        Button(
            onClick = {
                materialsVm.delete(material.id!!)
                navController.navigate("materials/${material.projectId}")
            }
        ) {
            Text(text = "Delete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}