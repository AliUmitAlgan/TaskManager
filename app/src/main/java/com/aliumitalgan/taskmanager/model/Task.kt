package com.aliumitalgan.taskmanager.model


import java.util.*

data class Task(
    val id: String = UUID.randomUUID().toString(),
    val title: String = "",
    val description: String = "",
    val dueDate: Long = System.currentTimeMillis(),
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val isCompleted: Boolean = false,
    val projectId: String = "",  // İlgili proje ile bağlantılı
    val userId: String = ""  // Kullanıcı ID'si (Firestore için gerekli)
) {
    constructor() : this("", "", "", System.currentTimeMillis(), TaskPriority.MEDIUM, false, "", "")
}