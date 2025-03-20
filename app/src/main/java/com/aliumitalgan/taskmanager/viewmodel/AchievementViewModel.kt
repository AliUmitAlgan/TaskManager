package com.aliumitalgan.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.aliumitalgan.taskmanager.model.Achievement
import com.aliumitalgan.taskmanager.model.AchievementType
import com.aliumitalgan.taskmanager.repository.AchievementRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class AchievementViewModel(
    private val repository: AchievementRepository
) : ViewModel() {

    private val _achievements = MutableStateFlow<List<Achievement>>(emptyList())
    val achievements: StateFlow<List<Achievement>> = _achievements.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllAchievements()
                .catch { e -> e.printStackTrace() }  // Hata yönetimi eklendi
                .collectLatest { achievementList ->
                    _achievements.value = achievementList
                }
        }
    }

    fun addAchievement(name: String, description: String, type: String, isAchieved: Boolean = false) {
        val achievement = Achievement(
            id = UUID.randomUUID().toString(),
            name = name,
            description = description,
            type = AchievementType.valueOf(type),
            unlocked = isAchieved,
            isNew = !isAchieved,
            achievedDate = if (isAchieved) System.currentTimeMillis() else null,
            createdAt = System.currentTimeMillis()
        )

        viewModelScope.launch {
            try {
                repository.addAchievement(achievement)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateAchievement(achievement: Achievement) {
        viewModelScope.launch {
            try {
                repository.updateAchievement(achievement)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteAchievement(achievementId: String) {
        viewModelScope.launch {
            try {
                repository.deleteAchievement(achievementId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Factory class to create instances of this ViewModel
    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AchievementViewModel::class.java)) {
                val repository = AchievementRepository()
                @Suppress("UNCHECKED_CAST")
                return AchievementViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}