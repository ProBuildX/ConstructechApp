import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.probuildx.constructechapp.entities.User
import com.probuildx.constructechapp.viewmodels.ProjectsViewModel
import com.probuildx.constructechapp.viewmodels.UsersViewModel
import com.probuildx.constructechapp.views.BottomNavigationBar

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
fun UserDashboard(navController: NavController,user: User, projects: List<Project>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Welcome Back!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = user.name,
            fontSize = 16.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = { navController.navigate("user-profile/${user.id}") },
            ) {
            Text(text = "Profile")
        }

        LazyColumn {
            items(projects, key = { it.id!! }) { project ->
                ProjectCard(navController = navController, project = project)
            }
        }

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
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            /*
            Image(
                painter = rememberImagePainter(data = project.imageUrl),
                contentDescription = "Project Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
                    .aspectRatio(1f)
                    .padding(end = 16.dp)
            )
            */
            Column {
                Text(
                    text = project.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
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
