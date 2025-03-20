package com.aliumitalgan.taskmanager.repository

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SettingsRepository(context: Context) {

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_settings", Context.MODE_PRIVATE)

    // Tema Modu (true: Koyu, false: Açık)
    private val _isDarkMode = MutableStateFlow(sharedPreferences.getBoolean("dark_mode", false))
    val isDarkMode: Flow<Boolean> = _isDarkMode.asStateFlow()

    // Bildirimler Açık mı?
    private val _notificationsEnabled =
        MutableStateFlow(sharedPreferences.getBoolean("notifications", true))
    val notificationsEnabled: Flow<Boolean> = _notificationsEnabled.asStateFlow()

    // Dil Ayarı (Varsayılan: Türkçe "tr")
    private val _selectedLanguage = MutableStateFlow(sharedPreferences.getString("language", "tr")!!)
    val selectedLanguage: Flow<String> = _selectedLanguage.asStateFlow()

    // Tema modunu değiştir
    fun setDarkMode(isDark: Boolean) {
        sharedPreferences.edit { putBoolean("dark_mode", isDark) }
        _isDarkMode.value = isDark
    }

    // Bildirimleri aç/kapat
    fun setNotificationsEnabled(enabled: Boolean) {
        sharedPreferences.edit { putBoolean("notifications", enabled) }
        _notificationsEnabled.value = enabled
    }

    // Dil ayarını değiştir
    fun setLanguage(languageCode: String) {
        sharedPreferences.edit { putString("language", languageCode) }
        _selectedLanguage.value = languageCode
    }
}
