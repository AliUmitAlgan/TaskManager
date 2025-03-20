package com.aliumitalgan.taskmanager.repository

import com.aliumitalgan.taskmanager.model.Project
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class ProjectRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val projectsCollection = firestore.collection("projects")

    // Kullanıcının projelerini getir
    fun getProjects(): Flow<List<Project>> = flow {
        val userId = auth.currentUser?.uid ?: return@flow
        val snapshot = projectsCollection.whereEqualTo("userId", userId).get().await()
        emit(snapshot.toObjects(Project::class.java))
    }

    // Proje ekle
    suspend fun addProject(project: Project) {
        val userId = auth.currentUser?.uid ?: throw IllegalStateException("User not authenticated")
        val newProject = project.copy(id = projectsCollection.document().id)
        projectsCollection.document(newProject.id).set(newProject).await()
    }

    // Proje güncelle
    suspend fun updateProject(project: Project) {
        if (auth.currentUser?.uid == project.id) {
            projectsCollection.document(project.id).set(project).await()
        }
    }

    // Proje sil
    suspend fun deleteProject(projectId: String) {
        projectsCollection.document(projectId).delete().await()
    }
}
