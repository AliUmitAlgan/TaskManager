package com.aliumitalgan.taskmanager.model

data class Achievement(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val type: AchievementType = AchievementType.GENERAL,  // Enum ile kategorize edelim
    val unlocked: Boolean = false,
    val isNew: Boolean = true,
    val achievedDate: Long? = null,  // Firestore timestamp formatı
    val createdAt: Long = System.currentTimeMillis()
) {
    constructor() : this("", "", "", AchievementType.GENERAL, false, true, null, System.currentTimeMillis())
}

enum class AchievementType {
    GENERAL, TASK_COMPLETION, PROJECT_MILESTONE, GOAL_REACHED
}
