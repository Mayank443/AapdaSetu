package com.example.aapdasetu.data.repository


import android.net.Uri
import com.example.aapdasetu.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

class UserRepository {
    private val uid = FirebaseAuth.getInstance().currentUser?.uid ?: throw Exception("User not logged in")
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun getUser(): User {
        val doc = db.collection("users").document(uid).get().await()
        return User(
            name = doc.getString("name") ?: "",
            email = doc.getString("email") ?: "",
            phone = doc.getString("phone") ?: "",
            profileImage = doc.getString("profileImage")
        )
    }

    suspend fun updateUser(user: User) {
        db.collection("users").document(uid).update(
            mapOf(
                "name" to user.name,
                "email" to user.email,
                "phone" to user.phone
            )
        ).await()
    }

    suspend fun uploadProfileImage(uri: Uri): String {
        val storageRef = storage.reference.child("Profile/$uid.jpg")
        storageRef.putFile(uri).await()
        return storageRef.downloadUrl.await().toString()
    }

    suspend fun updateProfileImageUrl(url: String) {
        db.collection("users").document(uid).update("profileImage", url).await()
    }
}
