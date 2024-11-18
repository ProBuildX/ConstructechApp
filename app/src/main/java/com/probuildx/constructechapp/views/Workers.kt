import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
import com.probuildx.constructechapp.entities.Worker
import com.probuildx.constructechapp.viewmodels.WorkersViewModel

@Composable
fun WorkersScreen(navController: NavController, projectId: Int) {
    Scaffold(
        topBar = {
            WorkersTopBar()
        },
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            WorkersList(navController = navController, projectId = projectId)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { navController.navigate("new-worker") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
                ) {
                    Text("NEW WORKER", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun WorkersTopBar() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "Workers & Teams",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        // Tab Bar
        TabRow(
            selectedTabIndex = 1,
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Summary", "Workers", "Teams").forEachIndexed { index, title ->
                Tab(
                    selected = index == 1,
                    onClick = {  },
                    text = {
                        Text(
                            text = title,
                            color = if (index == 1) Color.Black else Color.Gray,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun WorkersList(navController: NavController, projectId: Int, workersVm: WorkersViewModel = viewModel()) {
    val workers by workersVm.workers.collectAsState()
    val isLoading by workersVm.isLoading.collectAsState()
    val errorMessage by workersVm.errorMessage.collectAsState()

    LaunchedEffect(Unit) { workersVm.getByProject(projectId) }

    when {
        isLoading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
        errorMessage != null -> Text("$errorMessage", modifier = Modifier.fillMaxSize())
        else -> {
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                items(workers, key = { it.id!! }) { worker ->
                    WorkerCard(navController = navController, worker = worker)
                }
            }
        }
    }
}

@Composable
fun WorkerCard(navController: NavController, worker: Worker) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = worker.name,
                    fontSize = 16.sp,
                    color = Color.Black
                )
                Text(
                    text = worker.lastName,
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
            Row {
                IconButton(onClick = { /*falta implementar logica para editar*/ }) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color(0xFF5C6BC0)
                    )
                }
                IconButton(onClick = { /*falta implementar logica para eliminar*/ }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFD32F2F)
                    )
                }
            }
        }
    }
}