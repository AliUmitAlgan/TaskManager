package com.aliumitalgan.taskmanager.repository

import com.aliumitalgan.taskmanager.model.Achievement
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AchievementRepository {
    // Get FirebaseFirestore instance directly instead of injecting it
    private val db = FirebaseFirestore.getInstance()
    private val achievementCollection = db.collection("achievements")

    fun getAllAchievements(): Flow<List<Achievement>> = flow {
        try {
            val snapshot = achievementCollection.get().await()
            val achievements = snapshot.toObjects(Achievement::class.java)
            emit(achievements)
        } catch (e: Exception) {
            emit(emptyList()) // Return empty list on error
        }
    }

    suspend fun addAchievement(achievement: Achievement) {
        achievementCollection.document(achievement.id).set(achievement).await()
    }

    suspend fun updateAchievement(achievement: Achievement) {
        achievementCollection.document(achievement.id).set(achievement).await()
    }

    suspend fun deleteAchievement(achievementId: String) {
        achievementCollection.document(achievementId).delete().await()
    }
}