package com.aliumitalgan.taskmanager.ui.screen


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import com.aliumitalgan.taskmanager.ui.components.AchievementItem  // ✅ Eksik import eklendi
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.viewmodel.AchievementViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AchievementsScreen(
    navController: NavController,
    achievementViewModel: AchievementViewModel
) {
    val achievements by achievementViewModel.achievements.collectAsState(emptyList()) // Varsayılan değer eklendi
    val unlockedAchievements = remember(achievements) { achievements.filter { it.unlocked } }
    val lockedAchievements = remember(achievements) { achievements.filter { !it.unlocked } }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Achievements") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(text = "Your Progress", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = if (achievements.isNotEmpty())
                    unlockedAchievements.size.toFloat() / achievements.size.toFloat()
                else 0f,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${unlockedAchievements.size}/${achievements.size} achievements unlocked",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (unlockedAchievements.isNotEmpty()) {
                Text(text = "Unlocked", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(unlockedAchievements) { achievement ->
                        AchievementItem(achievement = achievement)
                    }

                }

                Spacer(modifier = Modifier.height(16.dp))
            }

            if (lockedAchievements.isNotEmpty()) {
                Text(text = "Locked", style = MaterialTheme.typography.titleMedium)

                Spacer(modifier = Modifier.height(8.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(lockedAchievements) { achievement ->
                        AchievementItem(achievement = achievement)
                    }

                }
            }
        }
    }
}
