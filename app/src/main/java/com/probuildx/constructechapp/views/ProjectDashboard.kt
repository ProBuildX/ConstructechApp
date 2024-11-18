import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel

@Composable
fun ProjectDashboardScreen(navController: NavController, projectId: Int, projectsVm: ProjectsViewModel = viewModel()) {
    val project by projectsVm.project.collectAsState()
    val isLoading by projectsVm.isLoading.collectAsState()
    val errorMessage by projectsVm.errorMessage.collectAsState()

    LaunchedEffect(projectId) { projectsVm.getById(projectId) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            when {
                isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                project == null -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
                else -> ProjectDashboard(navController, project!!)
            }
        }
    }
}

@Composable
fun ProjectDashboard(navController: NavController, project: Project) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Placeholder para imagen del proyecto
        Box(
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            // Aquí puedes agregar un Image si es necesario
        }

        // Título del proyecto
        Text(
            text = project.title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Descripción del proyecto
        Text(
            text = project.description ?: "",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Tarjetas de acción
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionCard(
                title = "Workers & Teams",
                onClick = { navController.navigate("workers/${project.id}") }
            )
            ActionCard(
                title = "Resource Management",
                onClick = { navController.navigate("resources/${project.id}") }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            ActionCard(
                title = "Tasks",
                onClick = { navController.navigate("tasks/${project.id}") }
            )
        }
    }
}

@Composable
fun ActionCard(title: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(140.dp)
            .height(120.dp)
            .padding(8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar(
        containerColor = Color.White
    ) {
        NavigationBarItem(
            selected = false,
            onClick = { navController.popBackStack() },
            label = { Text("Projects") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Projects") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("analytics") },
            label = { Text("Analytics") },
            icon = { Icon(Icons.Default.Menu, contentDescription = "Analytics") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("settings") },
            label = { Text("Settings") },
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") }
        )
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("profile") },
            label = { Text("Profile") },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") }
        )
    }
}
