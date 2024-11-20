package com.probuildx.constructechapp.views.resourcemanagement

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
import com.probuildx.constructechapp.entities.Machine
import com.probuildx.constructechapp.viewmodels.MachineryViewModel

@Composable
fun NewMachineScreen(navController: NavController, projectId: Int, machineryVm: MachineryViewModel = viewModel()) {

    //TODO: este es un mapa, como un dictionario de python
    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Start Date" to remember { mutableStateOf("") },
        "End Date" to remember { mutableStateOf("") },
        "Total Cost" to remember { mutableStateOf("") }
    )

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

        Button(
            onClick = {
                val newMachine = Machine(
                    name = formFields["Name"]?.value ?: "",
                    description = formFields["Description"]?.value ?: "",
                    startDate = formFields["Start Date"]?.value ?: "",
                    endDate = formFields["End Date"]?.value ?: "",
                    totalCost = formFields["Total Cost"]?.value ?: "",
                    projectId = projectId
                )

                machineryVm.create(newMachine)

                navController.navigate("machinery/${projectId}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }
}