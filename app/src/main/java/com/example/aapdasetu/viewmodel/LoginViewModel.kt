package com.example.aapdasetu.viewmodel

import androidx.lifecycle.ViewModel
import com.example.aapdasetu.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val loginState: StateFlow<UiState<Boolean>> = _loginState

    var email = MutableStateFlow("")
    var password = MutableStateFlow("")
    var agreeTerms = MutableStateFlow(false)
    var showDialog = MutableStateFlow(false)

    fun loginUser() {
        if (!agreeTerms.value) {
            _loginState.value = UiState.Error("Please agree to the User Agreement")
            return
        }

        if (email.value.isBlank() || password.value.isBlank()) {
            _loginState.value = UiState.Error("Email and password cannot be blank")
            return
        }

        _loginState.value = UiState.Loading

        FirebaseAuth.getInstance()
            .signInWithEmailAndPassword(email.value, password.value)
            .addOnSuccessListener {
                _loginState.value = UiState.Success(true)
            }
            .addOnFailureListener {
                _loginState.value = UiState.Error(it.message ?: "Unknown error occurred")
            }
    }

    fun resetState() {
        _loginState.value = UiState.Idle
    }
}
