package com.aliumitalgan.taskmanager

import HomeScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aliumitalgan.taskmanager.ui.screen.AchievementsScreen
import com.aliumitalgan.taskmanager.ui.screen.AddAchievementScreen
import com.aliumitalgan.taskmanager.ui.screen.AddGoalScreen
import com.aliumitalgan.taskmanager.ui.screen.AddProjectScreen
import com.aliumitalgan.taskmanager.ui.screen.AddTaskScreen
import com.aliumitalgan.taskmanager.ui.theme.TaskManagerTheme
import com.aliumitalgan.taskmanager.viewmodel.TaskViewModel
import com.aliumitalgan.taskmanager.viewmodel.ProjectViewModel
import com.aliumitalgan.taskmanager.viewmodel.GoalViewModel
import com.aliumitalgan.taskmanager.viewmodel.AchievementViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskManagerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // You can define your NavHost inside here
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = "home"
                    ) {
                        composable("home") { HomeScreen(navController, taskViewModel, projectViewModel, goalViewModel, achievementViewModel) }
                        composable("tasks") { TasksScreen(navController) }
                        composable("projects") { ProjectsScreen(navController) }
                        composable("goals") { GoalsScreen(navController) }
                        composable("achievements") { AchievementsScreen(navController) }
                        composable("add_task") { AddTaskScreen(navController) }
                        composable("add_project") { AddProjectScreen(navController) }
                        composable("add_goal") { AddGoalScreen(navController) }
                        composable("add_achievement") { AddAchievementScreen(navController) }
                    }
                }
            }
        }
    }
}
