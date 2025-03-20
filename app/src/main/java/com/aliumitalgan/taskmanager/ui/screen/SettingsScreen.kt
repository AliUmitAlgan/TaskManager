package com.aliumitalgan.taskmanager.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aliumitalgan.taskmanager.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel()
) {
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    val notificationsEnabled by viewModel.notificationsEnabled.collectAsState()
    val selectedLanguage by viewModel.selectedLanguage.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Ayarlar") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text("Uygulama Ayarları", style = MaterialTheme.typography.titleLarge)

            // Tema Değiştirici
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Koyu Mod")
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { viewModel.toggleDarkMode() }
                )
            }

            // Bildirim Aç/Kapat
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Bildirimleri Aç")
                Spacer(Modifier.weight(1f))
                Switch(
                    checked = notificationsEnabled,
                    onCheckedChange = { viewModel.toggleNotifications() }
                )
            }

            // Dil Seçme Dropdown
            var expanded by remember { mutableStateOf(false) }
            val languages = mapOf("tr" to "Türkçe", "en" to "English")

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = languages[selectedLanguage] ?: "Seçiniz",
                    onValueChange = {},
                    readOnly = true,
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    languages.forEach { (code, name) ->
                        DropdownMenuItem(
                            text = { Text(name) },
                            onClick = {
                                viewModel.changeLanguage(code)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}
