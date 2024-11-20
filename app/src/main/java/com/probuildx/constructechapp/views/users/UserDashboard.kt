package com.probuildx.constructechapp.views.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.probuildx.constructechapp.R
import com.probuildx.constructechapp.entities.Project
import com.probuildx.constructechapp.entities.User
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.UsersViewModel

@Composable
fun UserDashboardScreen(
    navController: NavController,
    userId: Int,
    usersVm: UsersViewModel = viewModel(),
    projectsVm: ProjectsViewModel = viewModel()
) {
    val user by usersVm.user.collectAsState()
    val isLoadingU by usersVm.isLoading.collectAsState()
    val errorMessageU by usersVm.errorMessage.collectAsState()

    val projects by projectsVm.projects.collectAsState()
    val isLoadingP by projectsVm.isLoading.collectAsState()
    val errorMessageP by projectsVm.errorMessage.collectAsState()

    LaunchedEffect(userId) {
        usersVm.getById(userId)
        projectsVm.getByUser(userId)
    }

    when {
        (isLoadingU || isLoadingP || user == null) ->
            CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessageU != null -> Text("$errorMessageU", modifier = Modifier.fillMaxSize())
        errorMessageP != null -> Text("$errorMessageP", modifier = Modifier.fillMaxSize())
        else -> {

            UserDashboard(navController, user!!, projects)

        }
    }
}

@Composable
fun UserDashboard(navController: NavController, user: User, projects: List<Project>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Welcome Back!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Text(
                    text = user.name,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }

            // Profile Icon/Button
            IconButton(
                onClick = { navController.navigate("user-profile/${user.id}") },
                modifier = Modifier
                    .size(48.dp)
                    .background(color = Color(0xFFEAEAEA), shape = CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Profile",
                    tint = Color(0xFF6A5ACD)
                )
            }
        }

        // Project List Header
        Text(
            text = "Your Projects",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Project List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(projects, key = { it.id!! }) { project ->
                ProjectCard(navController = navController, project = project)
            }
        }

        // New Project Button
        Button(
            onClick = { navController.navigate("new-project/${user.id}") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
        ) {
            Text(text = "NEW PROJECT", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
    }
}

@Composable
fun ProjectCard(navController: NavController, project: Project) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { navController.navigate("project-dashboard/${project.id}") },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9F9F9))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for Project Image
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .background(color = Color(0xFFEEE8AA), shape = CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_project_placeholder), // Replace with actual image resource or URL
                    contentDescription = "Project Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Project Details
            Column {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF333333)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = project.description,
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
        }
    }
}