package com.example.aapdasetu.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aapdasetu.data.model.User
import com.example.aapdasetu.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProfileViewModel : ViewModel() {
    private val repository = UserRepository()

    private val _user = MutableStateFlow(User())
    val user: StateFlow<User> = _user

    private val _profileImageUri = MutableStateFlow<Uri?>(null)
    val profileImageUri: StateFlow<Uri?> = _profileImageUri

    fun loadUser() {
        viewModelScope.launch {
            try {
                _user.value = repository.getUser()
            } catch (_: Exception) {}
        }
    }

    fun updateUser(onSuccess: () -> Unit, onError: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.updateUser(_user.value)
                onSuccess()
            } catch (e: Exception) {
                onError()
            }
        }
    }

    fun uploadProfileImage(uri: Uri, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                _profileImageUri.value = uri
                val url = repository.uploadProfileImage(uri)
                _user.value = _user.value.copy(profileImage = url)
                repository.updateProfileImageUrl(url)
                onSuccess()
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
            }
        }
    }

    fun onNameChange(name: String) {
        _user.value = _user.value.copy(name = name)
    }

    fun onEmailChange(email: String) {
        _user.value = _user.value.copy(email = email)
    }

    fun onPhoneChange(phone: String) {
        _user.value = _user.value.copy(phone = phone)
    }
}
