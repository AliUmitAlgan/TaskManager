package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.ui.components.ProjectItem
import com.aliumitalgan.taskmanager.viewmodel.ProjectViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectScreen(
    navController: NavController,
    projectViewModel: ProjectViewModel = viewModel()
) {
    val projects by projectViewModel.projects.collectAsState()
    val isLoading by projectViewModel.isLoading.collectAsState()
    val errorMessage by projectViewModel.errorMessage.collectAsState()

    LaunchedEffect(Unit) {
        projectViewModel.loadProjects()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Projeler") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("add_project") }) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Proje Ekle")
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                errorMessage != null -> {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Hata: $errorMessage", color = MaterialTheme.colorScheme.error)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { projectViewModel.loadProjects() }) {
                            Text("Tekrar Dene")
                        }
                    }
                }
                projects.isEmpty() -> {
                    Text("Henüz proje yok. Yeni bir proje ekleyin.")
                }
                else -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(projects) { project ->
                            ProjectItem(
                                project = project,
                                onClick = { navController.navigate("project_detail/${project.id}") }
                            )
                        }
                    }
                }
            }
        }
    }
}
