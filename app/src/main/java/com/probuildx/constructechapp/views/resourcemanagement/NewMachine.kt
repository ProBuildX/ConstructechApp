package com.probuildx.constructechapp.views.resourcemanagement

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Machine
import com.probuildx.constructechapp.viewmodels.MachineryViewModel

@ExperimentalMaterial3Api
@Composable
fun NewMachineScreen(
    navController: NavController,
    projectId: Int,
    machineryVm: MachineryViewModel = viewModel()
) {
    val formFields = mapOf(
        "Name" to remember { mutableStateOf("") },
        "Description" to remember { mutableStateOf("") },
        "Start Date" to remember { mutableStateOf("") },
        "End Date" to remember { mutableStateOf("") },
        "Total Cost" to remember { mutableStateOf("") }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Machine") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFF5F5F5))
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                // Formulario de entrada
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        formFields.forEach { (label, state) ->
                            OutlinedTextField(
                                value = state.value,
                                onValueChange = { state.value = it },
                                label = { Text(label) },
                                singleLine = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp)
                            )
                        }
                    }
                }

                // Botón para guardar
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("SAVE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
