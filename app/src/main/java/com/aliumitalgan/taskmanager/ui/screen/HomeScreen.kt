import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.aliumitalgan.taskmanager.viewmodel.*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    goalViewModel: GoalViewModel,
    achievementViewModel: AchievementViewModel
) {
    val tasks by taskViewModel.tasks.collectAsState(initial = emptyList())
    val projects by projectViewModel.projects.collectAsState(initial = emptyList())
    val goals by goalViewModel.goals.collectAsState(initial = emptyList())
    val achievements by achievementViewModel.achievements.collectAsState(initial = emptyList())

    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Tasks", "Projects", "Goals", "Achievements")

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Task Manager", style = MaterialTheme.typography.headlineLarge) },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.Settings, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(selectedTabIndex, tabs, navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    when (selectedTabIndex) {
                        0 -> navController.navigate("add_task")
                        1 -> navController.navigate("add_project")
                        2 -> navController.navigate("add_goal")
                        3 -> navController.navigate("add_achievement")
                    }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Welcome to Task Manager",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Task, Project, Goal, Achievement Cards
            HomeSummaryCard(
                title = "Tasks",
                count = tasks.size,
                icon = Icons.Filled.CheckCircle,
                progress = tasks.filter { it.isCompleted }.size.toFloat() / tasks.size.coerceAtLeast(1),
                onClick = { navController.navigate("tasks") }
            )

            HomeSummaryCard(
                title = "Projects",
                count = projects.size,
                icon = Icons.Filled.Folder,
                progress = projects.filter { it.isCompleted }.size.toFloat() / projects.size.coerceAtLeast(1),
                onClick = { navController.navigate("projects") }
            )

            HomeSummaryCard(
                title = "Goals",
                count = goals.size,
                icon = Icons.Filled.Star,
                progress = goals.filter { it.isCompleted }.size.toFloat() / goals.size.coerceAtLeast(1),
                onClick = { navController.navigate("goals") }
            )

            HomeSummaryCard(
                title = "Achievements",
                count = achievements.size,
                icon = Icons.Filled.EmojiEvents,
                progress = achievements.filter { it.isCompleted }.size.toFloat() / achievements.size.coerceAtLeast(1),
                onClick = { navController.navigate("achievements") }
            )
        }
    }
}

@Composable
fun BottomNavigationBar(
    selectedTabIndex: Int,
    tabs: List<String>,
    navController: NavHostController
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        tabs.forEachIndexed { index, title ->
            NavigationBarItem(
                icon = {
                    when (index) {
                        0 -> Icon(Icons.Filled.CheckCircle, contentDescription = title)
                        1 -> Icon(Icons.Filled.Folder, contentDescription = title)
                        2 -> Icon(Icons.Filled.Star, contentDescription = title)
                        3 -> Icon(Icons.Filled.EmojiEvents, contentDescription = title)
                    }
                },
                label = { Text(title) },
                selected = selectedTabIndex == index,
                onClick = {
                    selectedTabIndex = index
                    when (index) {
                        0 -> navController.navigate("tasks")
                        1 -> navController.navigate("projects")
                        2 -> navController.navigate("goals")
                        3 -> navController.navigate("achievements")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeSummaryCard(
    title: String,
    count: Int,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    progress: Float,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = "$count items",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Go to $title",
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = progress,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    }
}
