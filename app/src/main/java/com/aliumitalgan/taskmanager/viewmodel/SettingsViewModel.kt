package com.aliumitalgan.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aliumitalgan.taskmanager.repository.SettingsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    val isDarkMode = repository.isDarkMode.stateIn(viewModelScope, SharingStarted.Lazily, false)
    val notificationsEnabled = repository.notificationsEnabled.stateIn(viewModelScope, SharingStarted.Lazily, true)
    val selectedLanguage = repository.selectedLanguage.stateIn(viewModelScope, SharingStarted.Lazily, "tr")

    fun toggleDarkMode() {
        viewModelScope.launch {
            repository.setDarkMode(!isDarkMode.value)
        }
    }

    fun toggleNotifications() {
        viewModelScope.launch {
            repository.setNotificationsEnabled(!notificationsEnabled.value)
        }
    }

    fun changeLanguage(languageCode: String) {
        viewModelScope.launch {
            repository.setLanguage(languageCode)
        }
    }
}
