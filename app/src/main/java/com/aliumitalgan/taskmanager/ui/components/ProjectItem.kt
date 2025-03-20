package com.aliumitalgan.taskmanager.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.aliumitalgan.taskmanager.model.Project

@Composable
fun ProjectItem(
    project: Project,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Proje Başlığı
            Text(
                text = project.name,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Açıklama
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Görev Durumu (Tamamlanan / Toplam Görev)
            LinearProgressIndicator(
                progress = if (project.totalTasks > 0) {
                    project.completedTasks.toFloat() / project.totalTasks
                } else 0f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Görev Sayısı
            Text(
                text = "${project.completedTasks} / ${project.totalTasks} görev tamamlandı",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Öncelik Göstergesi
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(getPriorityColor(project.priority), shape = RoundedCornerShape(4.dp))
                    .padding(4.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = project.priority.name,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

// Proje Önceliğine Göre Renk Belirleme
@Composable
fun getPriorityColor(priority: com.aliumitalgan.taskmanager.model.TaskPriority): Color {
    return when (priority) {
        com.aliumitalgan.taskmanager.model.TaskPriority.HIGH -> Color.Red
        com.aliumitalgan.taskmanager.model.TaskPriority.MEDIUM -> Color.Yellow
        com.aliumitalgan.taskmanager.model.TaskPriority.LOW -> Color.Green
    }
}
