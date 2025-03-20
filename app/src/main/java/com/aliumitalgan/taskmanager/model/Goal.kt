
package com.aliumitalgan.taskmanager.model

data class Goal(
    val id: String = "", // Firestore için gerekli
    val title: String = "", // Hedefin başlığı
    val description: String = "", // Hedefin açıklaması
    val deadline: String = "", // Bitiş tarihi (YYYY-MM-DD formatında)
    val category: String = "", // Hedefin ait olduğu kategori
    val priority: Int = 2, // Öncelik seviyesi: 1=Düşük, 2=Orta, 3=Yüksek
    val userId: String = "", // Kullanıcı kimliği (hangi kullanıcıya ait?)
    val progress: Float = 0f, // Tamamlama yüzdesi (%0 - %100)
    val isCompleted: Boolean = false, // Hedef tamamlandı mı?
    val createdAt: Long = System.currentTimeMillis(), // Oluşturulma zamanı (Unix timestamp)
    val updatedAt: Long = System.currentTimeMillis() // Son güncelleme zamanı (Unix timestamp)
) {
    constructor() : this("", "", "", "", "", 2, "", 0f, false, 0, 0)
}
