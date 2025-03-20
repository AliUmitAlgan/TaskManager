package com.aliumitalgan.taskmanager.ui.navigation

import HomeScreen
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.aliumitalgan.taskmanager.ui.screen.*
import com.aliumitalgan.taskmanager.viewmodel.*
import com.aliumitalgan.taskmanager.ui.screen.AchievementsScreen


@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String = "login",
    authViewModel: AuthViewModel,
    taskViewModel: TaskViewModel,
    projectViewModel: ProjectViewModel,
    goalViewModel: GoalViewModel,
    achievementViewModel: AchievementViewModel, // ✅ Eksik olan eklendi
    settingsViewModel: SettingsViewModel
) {
    NavHost(navController = navController, startDestination = startDestination) {

        // ✅ Giriş & Kayıt
        composable("login") {
            LoginScreen(navController, authViewModel)
        }
        composable("register") {
            RegisterScreen(navController, authViewModel)
        }

        // ✅ Ana Sayfa
        composable("home") {
            HomeScreen(
                navController = navController,
                taskViewModel = taskViewModel,
                projectViewModel = projectViewModel,
                goalViewModel = goalViewModel,
                achievementViewModel = achievementViewModel // ✅ Eksik olan eklendi
            )
        }

        // ✅ Görevler
        composable("tasks") {
            TaskScreen(navController, taskViewModel)
        }
        composable("task_detail/{taskId}") { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId") ?: ""
            TaskDetailScreen(navController, taskId, taskViewModel)
        }
        composable("add_task") {
            AddTaskScreen(navController, taskViewModel.toString())
        }

        // ✅ Projeler
        composable("projects") {
            ProjectScreen(navController, projectViewModel)
        }
        composable("project_detail/{projectId}") { backStackEntry ->
            val projectId = backStackEntry.arguments?.getString("projectId") ?: ""
            ProjectDetailScreen(navController, projectId, projectViewModel)
        }
        composable("add_project") {
            AddProjectScreen(navController, projectViewModel)
        }

        // ✅ Hedefler
        composable("goals") {
            GoalScreen(navController, goalViewModel)
        }
        composable("goal_detail/{goalId}") { backStackEntry ->
            val goalId = backStackEntry.arguments?.getString("goalId") ?: ""
            GoalDetailScreen(navController, goalId, goalViewModel)
        }
        composable("add_goal") {
            AddGoalScreen(navController, goalViewModel)
        }

        // ✅ Başarılar
        composable("achievements") {
            AchievementsScreen(navController, achievementViewModel)
        }
        composable("add_achievement") {
            AddAchievementScreen(navController, achievementViewModel)
        }

        // ✅ Ayarlar
        composable("settings") {
            SettingsScreen(navController, settingsViewModel)
        }
    }
}
