package com.probuildx.constructechapp.views.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project

//TODO: arreglar iconos
@Composable
fun BottomNavigationBar(navController: NavController, project: Project) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("project-dashboard/${project.id}") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Dashboard") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("project-profile/${project.id}") },
            label = { Text("My Project") },
            icon = { Icon(Icons.Default.Menu, contentDescription = "Analytics") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("account/${project.userId}") },
            label = { Text("Account") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("user-dashboard/${project.userId}") },
            label = { Text("Leave") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
        )
    }
}