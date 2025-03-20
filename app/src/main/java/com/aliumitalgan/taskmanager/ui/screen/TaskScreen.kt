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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.ui.components.TaskItem
import com.aliumitalgan.taskmanager.viewmodel.TaskViewModel

@Composable
fun TaskScreen(
    navController: NavController,
    viewModel: TaskViewModel = viewModel()
) {
    val tasks by viewModel.tasks.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_task") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Görev")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (tasks.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Henüz görev bulunmuyor.")
                }
            } else {
                LazyColumn {
                    items(tasks) { task ->
                        TaskItem(
                            task = task,
                            onTaskClick = { navController.navigate("task_detail/${task.id}") },
                            onCompleteToggle = { isCompleted -> viewModel.updateTask(task.copy(isCompleted = isCompleted)) },
                            onDeleteClick = { viewModel.deleteTask(task.id) }
                        )
                    }
                }
            }
        }
    }
}
