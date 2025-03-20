package com.aliumitalgan.taskmanager.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDetailScreen(
    navController: NavController,
    taskId: String,
    viewModel: TaskViewModel = viewModel()
) {
    val task = viewModel.tasks.collectAsState().value.find { it.id == taskId }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Görev Detayı") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        task?.let {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(text = "Başlık: ${task.title}", style = MaterialTheme.typography.titleLarge)
                Text(text = "Açıklama: ${task.description}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Öncelik: ${task.priority}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Tarih: ${task.dueDate}", style = MaterialTheme.typography.bodyMedium)

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("edit_task/${task.id}") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Düzenle")
                }

                Button(
                    onClick = {
                        viewModel.deleteTask(task.id)
                        navController.popBackStack()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Sil")
                }
            }
        } ?: run {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Görev bulunamadı!")
            }
        }
    }
}
