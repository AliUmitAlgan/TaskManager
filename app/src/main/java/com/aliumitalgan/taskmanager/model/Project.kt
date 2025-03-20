package com.aliumitalgan.taskmanager.model


data class Project(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val completedTasks: Int = 0,
    val totalTasks: Int = 0,
    val dueDate: Long = System.currentTimeMillis(), // Firestore timestamp
    val priority: TaskPriority = TaskPriority.MEDIUM,
    val status: ProjectStatus = ProjectStatus.NOT_STARTED
) {
    val isOverdue: Boolean
        get() = dueDate < System.currentTimeMillis() // Otomatik gecikme hesaplama

    constructor() : this("", "", "", 0, 0, System.currentTimeMillis(), TaskPriority.MEDIUM, ProjectStatus.NOT_STARTED)
}
