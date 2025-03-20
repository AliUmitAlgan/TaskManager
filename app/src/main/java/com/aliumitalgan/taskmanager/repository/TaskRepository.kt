package com.aliumitalgan.taskmanager.repository

import com.aliumitalgan.taskmanager.model.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class TaskRepository {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val taskCollection = db.collection("tasks")

    fun getAllTasks(): Flow<List<Task>> = flow {
        val userId = auth.currentUser?.uid ?: return@flow
        val snapshot = taskCollection.whereEqualTo("userId", userId).get().await()
        val tasks = snapshot.toObjects(Task::class.java)
        emit(tasks)
    }

    suspend fun addTask(task: Task) {
        val newTask = task.copy(id = taskCollection.document().id)
        taskCollection.document(newTask.id).set(newTask).await()
    }

    suspend fun updateTask(task: Task) {
        taskCollection.document(task.id).set(task).await()
    }

    suspend fun deleteTask(taskId: String) {
        taskCollection.document(taskId).delete().await()
    }

    fun getTaskById(taskId: String): Flow<Task?> = flow {
        val snapshot = taskCollection.document(taskId).get().await()
        emit(snapshot.toObject(Task::class.java))
    }
}
