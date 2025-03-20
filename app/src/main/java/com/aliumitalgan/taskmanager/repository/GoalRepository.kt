package com.aliumitalgan.taskmanager.repository

import com.aliumitalgan.taskmanager.model.Goal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class GoalRepository {
    private val db = FirebaseFirestore.getInstance()
    private val goalCollection = db.collection("goals")

    fun getAllGoals(): Flow<List<Goal>> = flow {
        val snapshot = goalCollection.get().await()
        val goals = snapshot.toObjects(Goal::class.java)
        emit(goals)
    }

    suspend fun addGoal(goal: Goal) {
        goalCollection.document(goal.id).set(goal).await()
    }

    suspend fun updateGoal(goal: Goal) {
        goalCollection.document(goal.id).set(goal).await()
    }

    suspend fun deleteGoal(goalId: String) {
        goalCollection.document(goalId).delete().await()
    }

    // ✅ **Eksik olan `updateGoalProgress()` metodunu ekleyelim**
    suspend fun updateGoalProgress(goalId: String, progress: Float) {
        val goalRef = goalCollection.document(goalId)
        goalRef.update("progress", progress).await()
    }
}
