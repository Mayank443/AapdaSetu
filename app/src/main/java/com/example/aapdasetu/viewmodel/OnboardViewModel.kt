package com.example.aapdasetu.viewmodel

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aapdasetu.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OnboardViewModel : ViewModel() {
    var name = MutableStateFlow("")
    var email = MutableStateFlow("")
    var phone = MutableStateFlow("")
    var password = MutableStateFlow("")
    var imageUri = MutableStateFlow<Uri?>(null)
    var imageUrl = MutableStateFlow<String?>(null)
    var agreeTerms = MutableStateFlow(false)
    var showDialog = MutableStateFlow(false)

    private val auth = FirebaseAuth.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val firestore = FirebaseFirestore.getInstance()

    fun onImageSelected(context: Context, uri: Uri) {
        imageUri.value = uri
        val uid = auth.currentUser?.uid ?: "temp-${System.currentTimeMillis()}"
        val ref = storage.reference.child("Profile$uid/$uid.jpg")

        ref.putFile(uri)
            .continueWithTask { task ->
                if (!task.isSuccessful) throw task.exception ?: Exception("Upload failed")
                ref.downloadUrl
            }
            .addOnSuccessListener { downloadUrl ->
                imageUrl.value = downloadUrl.toString()
                Toast.makeText(context, "Image uploaded", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(context, "Upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }

    fun registerUser(context: Context, onSuccess: () -> Unit) {
        if (!agreeTerms.value) {
            Toast.makeText(context, "Please agree to the User Agreement", Toast.LENGTH_SHORT).show()
            return
        }
        if (email.value.isBlank() || password.value.isBlank() || name.value.isBlank() || phone.value.isBlank()) {
            Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener {
                val uid = auth.currentUser?.uid ?: return@addOnSuccessListener
                val user = User(name.value, email.value, phone.value, imageUrl.value)

                firestore.collection("users").document(uid).set(user)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                        viewModelScope.launch { onSuccess() }
                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Failed to save user data", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Auth error: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}