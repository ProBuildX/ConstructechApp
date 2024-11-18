package com.probuildx.constructechapp

import com.probuildx.constructechapp.views.projects.NewProjectScreen
import com.probuildx.constructechapp.views.projects.ProjectDashboardScreen
import com.probuildx.constructechapp.views.users.SignInScreen
import com.probuildx.constructechapp.views.users.SignUpScreen
import com.probuildx.constructechapp.views.users.UserDashboardScreen
import com.probuildx.constructechapp.views.staffmanagement.WorkersScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.probuildx.constructechapp.ui.theme.ConstructechappTheme
import com.probuildx.constructechapp.views.projects.AccountScreen
import com.probuildx.constructechapp.views.projects.ProjectProfileScreen
import com.probuildx.constructechapp.views.staffmanagement.StaffManagementScreen
import com.probuildx.constructechapp.views.users.UserProfileScreen

//TODO: cambiar los colores defecto del fondo(figma) y de los cards(blanco)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            ConstructechappTheme { App() }
        }
    }
}


@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "sign-in") {
        composable("sign-in") { SignInScreen(navController) }
        composable("sign-up") { SignUpScreen(navController) }
        composable("user-dashboard/{id}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("id")?.toInt()
            userId?.let { UserDashboardScreen(navController, userId = it) }
        }
        composable("new-project/{id}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("id")?.toInt()
            userId?.let { NewProjectScreen(navController, userId = it) }
        }
        composable("user-profile/{id}") { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("id")?.toInt()
            userId?.let { UserProfileScreen(navController, userId = it) }
        }
        composable("project-dashboard/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { ProjectDashboardScreen(navController, projectId = it) }
        }
        composable("project-profile/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { ProjectProfileScreen(navController, projectId = it) }
        }
        composable("account/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { AccountScreen(navController, projectId = it) }
        }
        composable("staff-management/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { StaffManagementScreen(navController, projectId = it) }
        }
        composable("workers/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { WorkersScreen(navController, projectId = it) }
        }
    }
}



