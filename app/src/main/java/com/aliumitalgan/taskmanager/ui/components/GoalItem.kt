package com.aliumitalgan.taskmanager.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.aliumitalgan.taskmanager.model.Goal

@Composable
fun GoalItem(
    goal: Goal,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onProgressChange: (Float) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(goal.title, style = MaterialTheme.typography.headlineSmall)
            Text(goal.description, style = MaterialTheme.typography.bodyMedium)
            Text("Kategori: ${goal.category}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(8.dp))

            // Progress Bar
            LinearProgressIndicator(
                progress = goal.progress,
                modifier = Modifier.fillMaxWidth(),
                color = if (goal.isCompleted) Color.Green else Color.Blue
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Tamamlandı simgesi
                if (goal.isCompleted) {
                    Icon(Icons.Default.CheckCircle, contentDescription = "Tamamlandı", tint = Color.Green)
                }

                // Butonlar
                Row {
                    IconButton(onClick = onEditClick) {
                        Icon(Icons.Default.Edit, contentDescription = "Düzenle")
                    }
                    IconButton(onClick = onDeleteClick) {
                        Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red)
                    }
                }
            }
        }
    }
}
