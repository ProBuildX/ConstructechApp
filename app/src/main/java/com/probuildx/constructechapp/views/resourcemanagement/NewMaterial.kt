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
import com.probuildx.constructechapp.entities.Material
import com.probuildx.constructechapp.viewmodels.MaterialsViewModel

@Composable
fun NewMaterialScreen(navController: NavController, projectId: Int, materialsVm: MaterialsViewModel = viewModel()) {

    //TODO: este es un mapa, como un dictionario de python
    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Amount" to remember { mutableStateOf("") },
        "Reception Date" to remember { mutableStateOf("") },
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
                val newMaterial = Material(
                    name = formFields["Name"]?.value ?: "",
                    description = formFields["Description"]?.value ?: "",
                    amount = formFields["Amount"]?.value ?: "",
                    receptionDate = formFields["Reception Date"]?.value ?: "",
                    totalCost = formFields["Total Cost"]?.value ?: "",
                    projectId = projectId
                )

                materialsVm.create(newMaterial)

                navController.navigate("materials/${projectId}")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("SAVE")
        }
    }
}