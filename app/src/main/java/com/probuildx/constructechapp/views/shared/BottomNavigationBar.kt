package com.probuildx.constructechapp.views.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project

@Composable
fun BottomNavigationBar(navController: NavController, project: Project) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        // Home Navigation
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("project-dashboard/${project.id}") },
            label = { Text("Home") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") }
        )
        // My Project Navigation
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("project-profile/${project.id}") },
            label = { Text("My Project") },
            icon = { Icon(Icons.Default.Build, contentDescription = "My Project") }
        )
        // Account Navigation
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("account/${project.userId}") },
            label = { Text("Account") },
            icon = { Icon(Icons.Default.AccountCircle, contentDescription = "Account") }
        )
        // Leave Navigation
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("user-dashboard/${project.userId}") },
            label = { Text("Leave") },
            icon = { Icon(Icons.Default.ExitToApp, contentDescription = "Leave") }
        )
    }
}
