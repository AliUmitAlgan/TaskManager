package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.ui.components.GoalItem
import com.aliumitalgan.taskmanager.viewmodel.GoalViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalScreen(
    navController: NavController,
    goalViewModel: GoalViewModel = viewModel()
) {
    val goals by goalViewModel.goals.collectAsState()

    LaunchedEffect(Unit) {
        goalViewModel.loadGoals()
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Hedeflerim") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_goal") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Yeni Hedef Ekle")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (goals.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Henüz hedef eklenmemiş.")
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(goals) { goal ->
                        GoalItem(
                            goal = goal,
                            onEditClick = { navController.navigate("edit_goal/${goal.id}") },
                            onDeleteClick = { goalViewModel.deleteGoal(goal.id) },
                            onProgressChange = { progress -> goalViewModel.updateGoalProgress(goal.id, progress) }
                        )
                    }
                }
            }
        }
    }
}
