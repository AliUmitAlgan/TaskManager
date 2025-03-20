package com.aliumitalgan.taskmanager.repository

import com.aliumitalgan.taskmanager.model.User
import com.aliumitalgan.taskmanager.model.UserRole
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val usersCollection = firestore.collection("users")

    // Kullanıcı kayıt işlemi
    suspend fun registerUser(name: String, email: String, password: String): String {
        val authResult = auth.createUserWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw IllegalStateException("Kullanıcı oluşturulamadı")

        // Kullanıcı bilgilerini Firestore'a kaydet
        val newUser = User(
            id = userId,
            name = name,
            email = email,
            profileImageUrl = null,
            createdAt = System.currentTimeMillis(),
            lastLogin = System.currentTimeMillis(),
            achievements = emptyList(),
            completedTasks = 0,
            role = UserRole.USER
        )

        usersCollection.document(userId).set(newUser).await()
        return userId
    }

    // Kullanıcı giriş işlemi
    suspend fun loginUser(email: String, password: String): String {
        val authResult = auth.signInWithEmailAndPassword(email, password).await()
        val userId = authResult.user?.uid ?: throw IllegalStateException("Giriş başarısız")

        // Son giriş tarihini güncelle
        usersCollection.document(userId).update("lastLogin", System.currentTimeMillis()).await()
        return userId
    }

    // Kullanıcı çıkış işlemi
    fun logoutUser() {
        auth.signOut()
    }

    // Mevcut oturum açmış kullanıcıyı al
    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    // Kullanıcının profil bilgilerini getir
    suspend fun getUserData(userId: String): User? {
        return usersCollection.document(userId).get().await().toObject(User::class.java)
    }

    // Kullanıcı adını güncelle
    suspend fun updateUserName(userId: String, newName: String) {
        auth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()
        )?.await()

        usersCollection.document(userId).update("name", newName).await()
    }

    // Kullanıcının profil resmini güncelle
    suspend fun updateUserProfileImage(userId: String, imageUrl: String) {
        auth.currentUser?.updateProfile(
            UserProfileChangeRequest.Builder()
                .setPhotoUri(android.net.Uri.parse(imageUrl))
                .build()
        )?.await()

        usersCollection.document(userId).update("profileImageUrl", imageUrl).await()
    }

    // Kullanıcı şifresini güncelle
    suspend fun updateUserPassword(newPassword: String) {
        auth.currentUser?.updatePassword(newPassword)?.await()
    }

    // Kullanıcıyı tamamen sil
    suspend fun deleteUser(userId: String) {
        usersCollection.document(userId).delete().await()
        auth.currentUser?.delete()?.await()
    }
}
