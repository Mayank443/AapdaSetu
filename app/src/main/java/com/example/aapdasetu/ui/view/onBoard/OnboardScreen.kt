package com.example.aapdasetu.ui.view.onBoard

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.aapdasetu.ui.view.ClickableText
import com.example.aapdasetu.ui.view.UserAgreementDraft
import com.example.aapdasetu.viewmodel.OnboardViewModel

@Composable
fun OnboardScreen(
    viewModel: OnboardViewModel = viewModel(),
    onRegisterSuccess: () -> Unit,
    onLogin: () -> Unit
) {
    val context = LocalContext.current

    val showDialog by viewModel.showDialog.collectAsState()

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> uri?.let { viewModel.onImageSelected(context, it) } }

    UserAgreementDraft(
        showDialog = showDialog,
        onCancel = { viewModel.agreeTerms.value = false; viewModel.showDialog.value = false },
        onAgree = { viewModel.agreeTerms.value = true; viewModel.showDialog.value = false }
    )

    Scaffold(modifier = Modifier.padding(16.dp)) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            HeaderSection()
            ProfileImageSection(rememberAsyncImagePainter(viewModel.imageUri.collectAsState().value),viewModel, galleryLauncher)
            InputFields(viewModel)
            AgreementRow(viewModel)
            RegisterButton(viewModel, context, onRegisterSuccess)
            ClickableText(
                "Already have an account? ",
                "Login",
                Color(0xFFFC8414),
                Modifier.align(Alignment.CenterHorizontally),
                onLogin
            )
        }
    }
}








