package com.probuildx.constructechapp.views.projects

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel

@ExperimentalMaterial3Api
@Composable
fun NewProjectScreen(navController: NavController, userId: Int, projectsVm: ProjectsViewModel = viewModel()) {

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    val budget = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Project") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color(0xFFFF9800))
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Decorative Header
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFFF9800), Color(0xFFFFC107))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Start Building Your Dream Project",
                        style = MaterialTheme.typography.titleLarge.copy(color = Color.White),
                        modifier = Modifier.padding(horizontal = 16.dp),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Form Fields
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    TextField(
                        value = title.value,
                        onValueChange = { title.value = it },
                        label = { Text("Title") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = description.value,
                        onValueChange = { description.value = it },
                        label = { Text("Description") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = address.value,
                        onValueChange = { address.value = it },
                        label = { Text("Address") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = date.value,
                        onValueChange = { date.value = it },
                        label = { Text("Date") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    TextField(
                        value = budget.value,
                        onValueChange = { budget.value = it },
                        label = { Text("Budget") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            val newProject = Project(
                                title = title.value,
                                description = description.value,
                                address = address.value,
                                date = date.value,
                                budget = budget.value,
                                userId = userId
                            )

                            projectsVm.create(newProject)
                            navController.navigate("user-dashboard/$userId")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                    ) {
                        Text(text = "SAVE", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }
                }
            }
        }
    )
}
