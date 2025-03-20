package com.aliumitalgan.taskmanager.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val profileImageUrl: String? = null, // Kullanıcının profil resmi URL'si
    val createdAt: Long = System.currentTimeMillis(), // Hesap oluşturma tarihi
    val lastLogin: Long? = null, // Son giriş tarihi
    val achievements: List<String> = emptyList(), // Kazandığı başarımlar
    val completedTasks: Int = 0, // Tamamlanan görev sayısı
    val role: UserRole = UserRole.USER // Kullanıcı rolü (Admin vs. normal kullanıcı)
) {
    constructor() : this("", "", "", null, System.currentTimeMillis(), null, emptyList(), 0, UserRole.USER)
}

// Kullanıcı rollerini belirlemek için enum
enum class UserRole {
    ADMIN, USER
}
