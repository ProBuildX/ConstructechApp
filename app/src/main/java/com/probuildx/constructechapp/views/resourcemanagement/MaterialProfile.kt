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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color


@ExperimentalMaterial3Api
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

@ExperimentalMaterial3Api
@Composable
fun MaterialProfile(navController: NavController, material: Material, materialsVm: MaterialsViewModel = viewModel()) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Material Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back to Materials"
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
                // Tarjeta con detalles del material
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9)),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "Name",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = material.name,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = material.description ?: "No description provided",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Amount",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = "${material.amount}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Reception Date",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = material.receptionDate,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )

                        Divider()

                        Text(
                            text = "Total Cost",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            color = Color(0xFF333333)
                        )
                        Text(
                            text = "${material.totalCost}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Gray
                        )
                    }
                }

                // Botón para eliminar el material
                Button(
                    onClick = {
                        materialsVm.delete(material.id!!)
                        navController.navigate("materials/${material.projectId}")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text(text = "DELETE MATERIAL", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }
            }
        }
    }
}
