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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ExperimentalComposeApi
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.composable
import com.probuildx.constructechapp.ui.theme.ConstructechappTheme
import com.probuildx.constructechapp.views.projects.AccountScreen
import com.probuildx.constructechapp.views.projects.ProjectProfileScreen
import com.probuildx.constructechapp.views.resourcemanagement.MachineProfileScreen
import com.probuildx.constructechapp.views.resourcemanagement.MachineryScreen
import com.probuildx.constructechapp.views.resourcemanagement.MaterialProfileScreen
import com.probuildx.constructechapp.views.resourcemanagement.MaterialsScreen
import com.probuildx.constructechapp.views.resourcemanagement.NewMachineScreen
import com.probuildx.constructechapp.views.resourcemanagement.NewMaterialScreen
import com.probuildx.constructechapp.views.resourcemanagement.ResourceManagementScreen
import com.probuildx.constructechapp.views.staffmanagement.NewTeamScreen
import com.probuildx.constructechapp.views.staffmanagement.NewWorkerScreen
import com.probuildx.constructechapp.views.staffmanagement.StaffManagementScreen
import com.probuildx.constructechapp.views.staffmanagement.TeamProfileScreen
import com.probuildx.constructechapp.views.staffmanagement.TeamsScreen
import com.probuildx.constructechapp.views.staffmanagement.WorkerProfileScreen
import com.probuildx.constructechapp.views.tasks.NewTaskScreen
import com.probuildx.constructechapp.views.tasks.TaskProfileScreen
import com.probuildx.constructechapp.views.tasks.TasksScreen
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


@OptIn(ExperimentalMaterial3Api::class)
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
        composable("new-worker/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { NewWorkerScreen(navController, projectId = it) }
        }
        composable("worker-profile/{id}") { backStackEntry ->
            val workerId = backStackEntry.arguments?.getString("id")?.toInt()
            workerId?.let { WorkerProfileScreen(navController, workerId = it) }
        }
        composable("resource-management/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { ResourceManagementScreen(navController, projectId = it) }
        }
        composable("materials/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { MaterialsScreen(navController, projectId = it) }
        }
        composable("new-material/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { NewMaterialScreen(navController, projectId = it) }
        }
        composable("material-profile/{id}") { backStackEntry ->
            val materialId = backStackEntry.arguments?.getString("id")?.toInt()
            materialId?.let { MaterialProfileScreen(navController, materialId = it) }
        }
        composable("machinery/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { MachineryScreen(navController, projectId = it) }
        }
        composable("new-machine/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { NewMachineScreen(navController, projectId = it) }
        }
        composable("machine-profile/{id}") { backStackEntry ->
            val machineId = backStackEntry.arguments?.getString("id")?.toInt()
            machineId?.let { MachineProfileScreen(navController, machineId = it) }
        }
        composable("teams/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { TeamsScreen(navController, projectId = it) }
        }
        composable("new-team/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { NewTeamScreen(navController, projectId = it) }
        }
        composable("team-profile/{id}") { backStackEntry ->
            val teamId = backStackEntry.arguments?.getString("id")?.toInt()
            teamId?.let { TeamProfileScreen(navController, teamId = it) }
        }
        composable("tasks/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { TasksScreen(navController, projectId = it) }
        }
        composable("new-task/{id}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("id")?.toInt()
            projectId?.let { NewTaskScreen(navController, projectId = it) }
        }
        composable("task-profile/{id}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("id")?.toInt()
            taskId?.let { TaskProfileScreen(navController, taskId = it) }
        }
    }
}



