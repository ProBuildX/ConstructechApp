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
import com.probuildx.constructechapp.entities.Machine
import com.probuildx.constructechapp.viewmodels.MachineryViewModel


@Composable
fun MachineProfileScreen(navController: NavController, machineId: Int, machineryVm: MachineryViewModel = viewModel()) {
    val machine by machineryVm.machine.collectAsState()
    val isLoading by machineryVm.isLoading.collectAsState()
    val errorMessage by machineryVm.errorMessage.collectAsState()

    LaunchedEffect(machineId) { machineryVm.getById(machineId) }

    when {
        (isLoading || machine == null) -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {

            MachineProfile(navController, machine!!)

        }
    }
}

@Composable
fun MachineProfile(navController: NavController, machine: Machine, machineryVm: MachineryViewModel = viewModel()) {
    //TODO: mejorar interfaz
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Name: ${machine.name} ")
        Text(text = "Description: ${machine.description} ")
        Text(text = "Start Date: ${machine.startDate} ")
        Text(text = "End Date: ${machine.endDate} ")
        Text(text = "Total Cost: ${machine.totalCost} ")

        Button(
            onClick = {
                machineryVm.delete(machine.id!!)
                navController.navigate("machinery/${machine.projectId}")
            }
        ) {
            Text(text = "Delete", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }

    }

}