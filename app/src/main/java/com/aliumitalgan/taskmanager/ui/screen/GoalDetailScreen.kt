package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.model.Goal
import com.aliumitalgan.taskmanager.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalDetailScreen(
    navController: NavController,
    goalId: String,
    goalViewModel: GoalViewModel = viewModel()
) {
    val goal by goalViewModel.getGoalById(goalId).collectAsState(initial = null)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hedef Detayı") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Geri")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            goal?.let {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = it.title, style = MaterialTheme.typography.headlineMedium)
                    Text(text = it.description, style = MaterialTheme.typography.bodyLarge)
                    Text(text = "Bitiş Tarihi: ${it.deadline}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Kategori: ${it.category}", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "Öncelik: ${it.priority}", style = MaterialTheme.typography.bodyMedium)

                    // Tamamlandı Toggle
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tamamlandı mı?")
                        Spacer(modifier = Modifier.width(8.dp))
                        Switch(
                            checked = it.isCompleted,
                            onCheckedChange = { completed ->
                                val updatedGoal = it.copy(isCompleted = completed)
                                goalViewModel.updateGoal(updatedGoal)
                            }
                        )
                    }

                    // Düzenleme ve Silme Butonları
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = { navController.navigate("edit_goal/${goalId}") }
                        ) {
                            Icon(Icons.Default.Edit, contentDescription = "Düzenle")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Düzenle")
                        }

                        Button(
                            onClick = {
                                goalViewModel.deleteGoal(goalId)
                                navController.popBackStack()
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Sil")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Sil")
                        }
                    }
                }
            } ?: run {
                Text("Hedef bulunamadı!", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}
