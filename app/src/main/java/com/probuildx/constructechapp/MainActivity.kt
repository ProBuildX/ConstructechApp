package com.probuildx.constructechapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.probuildx.constructechapp.ui.theme.ConstructechappTheme
import com.probuildx.constructechapp.views.HomeScreen
import com.probuildx.constructechapp.views.NewProjectScreen
import com.probuildx.constructechapp.views.ProjectDashboardScreen
import com.probuildx.constructechapp.views.ProjectsScreen
import com.probuildx.constructechapp.views.WorkersScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ConstructechappTheme {
                App()
            }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "homes") {
        composable("homes") { HomeScreen(navController) }
        composable("projects") { ProjectsScreen(navController) }
        composable("new-project") { NewProjectScreen(navController) }
        composable("project-dashboard/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { ProjectDashboardScreen(navController, projectId = it) }
        }
        composable("workers/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { WorkersScreen(navController, projectId = it) }
        }
    }
}



