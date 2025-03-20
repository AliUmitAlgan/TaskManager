package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.model.Goal
import com.aliumitalgan.taskmanager.model.TaskPriority
import com.aliumitalgan.taskmanager.ui.components.PriorityDropdown
import com.aliumitalgan.taskmanager.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGoalScreen(
    navController: NavController,
    goalViewModel: GoalViewModel = viewModel(),
    goalId: String? = null
) {
    val existingGoal by goalViewModel.getGoalById(goalId ?: "").collectAsState(initial = null)

    var title by remember { mutableStateOf(existingGoal?.title ?: "") }
    var description by remember { mutableStateOf(existingGoal?.description ?: "") }
    var deadline by remember { mutableStateOf(existingGoal?.deadline ?: "") }
    var category by remember { mutableStateOf(existingGoal?.category ?: "") }
    var priority by remember { mutableStateOf(existingGoal?.priority ?: TaskPriority.MEDIUM) }

    LaunchedEffect(existingGoal) {
        existingGoal?.let {
            title = it.title
            description = it.description
            deadline = it.deadline
            category = it.category
            priority = it.priority
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (goalId != null) "Hedefi Düzenle" else "Yeni Hedef Ekle") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Geri")
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
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Başlık") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Açıklama") },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 3
            )

            OutlinedTextField(
                value = deadline,
                onValueChange = { deadline = it },
                label = { Text("Bitiş Tarihi (YYYY-MM-DD)") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Kategori") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            PriorityDropdown(priority as TaskPriority) { selectedPriority ->
                priority = selectedPriority
            }

            Button(
                onClick = {
                    if (title.isNotBlank() && description.isNotBlank() && deadline.isNotBlank() && category.isNotBlank()) {
                        if (goalId == null) {
                            goalViewModel.addGoal(
                                Goal(
                                    title = title,
                                    description = description,
                                    deadline = deadline,
                                    category = category,
                                    priority = (priority as TaskPriority).ordinal // TaskPriority -> Int dönüşümü
                                )
                            )
                        } else {
                            val updatedGoal = existingGoal?.copy(
                                title = title,
                                description = description,
                                deadline = deadline,
                                category = category,
                                priority = (priority as TaskPriority).ordinal
                            )
                            if (updatedGoal != null) {
                                goalViewModel.updateGoal(updatedGoal)
                            }
                        }
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && description.isNotBlank() && deadline.isNotBlank() && category.isNotBlank()
            ) {
                Text(if (goalId != null) "Hedefi Güncelle" else "Hedefi Kaydet")
            }
        }
    }
}
