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
import com.aliumitalgan.taskmanager.model.Project
import com.aliumitalgan.taskmanager.model.ProjectStatus
import com.aliumitalgan.taskmanager.model.TaskPriority
import com.aliumitalgan.taskmanager.ui.components.PriorityDropdown
import com.aliumitalgan.taskmanager.viewmodel.ProjectViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProjectScreen(
    navController: NavController,
    projectViewModel: ProjectViewModel = viewModel(),
    projectId: String? = null
) {
    val existingProject = projectViewModel.getProjectById(projectId).collectAsState(initial = null).value

    var name by remember { mutableStateOf(existingProject?.name ?: "") }
    var description by remember { mutableStateOf(existingProject?.description ?: "") }
    var dueDate by remember { mutableStateOf(existingProject?.dueDate.toString()) }
    var priority by remember { mutableStateOf(existingProject?.priority ?: TaskPriority.MEDIUM) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (projectId != null) "Projeyi Düzenle" else "Yeni Proje Ekle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Proje Adı") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Açıklama") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            OutlinedTextField(
                value = dueDate,
                onValueChange = { dueDate = it },
                label = { Text("Bitiş Tarihi (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            PriorityDropdown(priority) { selectedPriority ->
                priority = selectedPriority
            }

            Button(
                onClick = {
                    if (name.isNotBlank() && description.isNotBlank()) {
                        if (projectId == null) {
                            projectViewModel.addProject(
                                Project(
                                    name = name,
                                    description = description,
                                    dueDate = dueDate.toLong(),
                                    priority = priority,
                                    status = ProjectStatus.NOT_STARTED
                                )
                            )
                        } else {
                            val updatedProject = existingProject?.copy(
                                name = name,
                                description = description,
                                dueDate = dueDate.toLong(),
                                priority = priority
                            )
                            if (updatedProject != null) {
                                projectViewModel.updateProject(updatedProject)
                            }
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && description.isNotBlank()
            ) {
                Text(if (projectId != null) "Projeyi Güncelle" else "Projeyi Kaydet")
            }
        }
    }
}
