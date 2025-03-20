package com.aliumitalgan.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumitalgan.taskmanager.model.Goal
import com.aliumitalgan.taskmanager.repository.GoalRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GoalViewModel(private val repository: GoalRepository) : ViewModel() {

    private val _goals = MutableStateFlow<List<Goal>>(emptyList())
    val goals = _goals.asStateFlow()

    fun loadGoals() {
        viewModelScope.launch {
            repository.getAllGoals().collect { _goals.value = it }
        }
    }

    fun getGoalById(goalId: String): StateFlow<Goal?> {
        val goal = _goals.value.find { it.id == goalId }
        return MutableStateFlow(goal).asStateFlow()
    }


    fun addGoal(goal: Goal) {
        viewModelScope.launch {
            repository.addGoal(goal)
            loadGoals() // Güncellenmiş listeyi çek
        }
    }

    fun updateGoal(goal: Goal) {
        viewModelScope.launch {
            repository.updateGoal(goal)
            loadGoals()
        }
    }

    fun deleteGoal(goalId: String) {
        viewModelScope.launch {
            repository.deleteGoal(goalId)
            loadGoals()
        }
    }

    // ✅ **Eksik olan `updateGoalProgress` metodunu ekleyelim**
    fun updateGoalProgress(goalId: String, progress: Float) {
        viewModelScope.launch {
            repository.updateGoalProgress(goalId, progress)
            loadGoals()
        }
    }
}
