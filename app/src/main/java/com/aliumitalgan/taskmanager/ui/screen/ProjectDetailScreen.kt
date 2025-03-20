package com.aliumitalgan.taskmanager.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.model.Project
import com.aliumitalgan.taskmanager.viewmodel.ProjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    navController: NavController,
    projectId: String,
    projectViewModel: ProjectViewModel
) {
    val project = projectViewModel.projects.collectAsState().value.find { it.id == projectId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Proje Detayı") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri Dön")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            project?.let {
                Text("Adı: ${it.name}", style = MaterialTheme.typography.titleMedium)
                Text("Açıklama: ${it.description}")
                Text("Durum: ${it.status}")
                Text("Öncelik: ${it.priority}")
            } ?: Text("Proje bulunamadı.")
        }
    }
}
