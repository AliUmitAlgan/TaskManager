package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.model.Task
import com.aliumitalgan.taskmanager.model.TaskPriority
import com.aliumitalgan.taskmanager.ui.components.PriorityDropdown
import com.aliumitalgan.taskmanager.viewmodel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    navController: NavController,
    taskId: String? = null,
    viewModel: TaskViewModel = viewModel()
) {
    val existingTask = viewModel.tasks.collectAsState().value.find { it.id == taskId }

    var title by remember { mutableStateOf(existingTask?.title ?: "") }
    var description by remember { mutableStateOf(existingTask?.description ?: "") }
    var dueDate by remember { mutableStateOf(existingTask?.dueDate?.toString() ?: "") }
    var priority by remember { mutableStateOf(existingTask?.priority ?: TaskPriority.MEDIUM) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (taskId != null) "Görevi Düzenle" else "Yeni Görev Ekle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
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
            // Başlık
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Açıklama
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Açıklama") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            // Bitiş Tarihi
            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Bitiş Tarihi (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            // Öncelik Seçimi
            PriorityDropdown(priority) { selectedPriority ->
                priority = selectedPriority
            }

            // Kaydet/Güncelle Butonu
            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && dueDate.isNotBlank()) {
                        if (taskId == null) {
                            // Yeni görev ekle
                            viewModel.addTask(
                                Task(
                                    title = title,
                                    description = description,
                                    dueDate = dueDate.toLong(),
                                    priority = priority
                                )
                            )
                        } else {
                            // Mevcut görevi güncelle
                            val updatedTask = existingTask?.copy(
                                title = title,
                                description = description,
                                dueDate = dueDate.toLong(),
                                priority = priority
                            )
                            if (updatedTask != null) {
                                viewModel.updateTask(updatedTask)
                            }
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && description.isNotBlank() && dueDate.isNotBlank()
            ) {
                Text(if (taskId != null) "Güncelle" else "Kaydet")
            }
        }
    }
}



