package com.example.aapdasetu.ui.view.login

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aapdasetu.ui.view.Button
import com.example.aapdasetu.ui.view.ClickableText
import com.example.aapdasetu.ui.view.UserAgreementDraft
import com.example.aapdasetu.utils.UiState
import com.example.aapdasetu.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = viewModel(),
    onLoginSuccess: () -> Unit,
    onRegister: () -> Unit,
) {
    val context = LocalContext.current

    val showDialog by viewModel.showDialog.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    UserAgreementDraft(
        showDialog = showDialog,
        onCancel = {
            viewModel.showDialog.value = false; viewModel.agreeTerms.value = false
        },
        onAgree = {
            viewModel.showDialog.value = false; viewModel.agreeTerms.value = true
        }
    )

    LaunchedEffect(loginState) {
        when (val state = loginState) {
            is UiState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            is UiState.Success -> {
                Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                onLoginSuccess()
                viewModel.resetState()
            }
            else -> {}
        }
    }

    Scaffold(
        modifier = Modifier.padding(24.dp)
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HeaderSection()
            InputFields(viewModel)
            AgreementRow(viewModel)
            Button(Modifier.padding(8.dp).fillMaxWidth().height(64.dp), "Login", FontWeight.Normal, 24) {viewModel.loginUser() }
            Spacer(Modifier.height(8.dp))
            ClickableText("Do not have an account? ","Register", Color(0xFFFC8414),Modifier.align(Alignment.CenterHorizontally)) { onRegister }
        }
    }
}
