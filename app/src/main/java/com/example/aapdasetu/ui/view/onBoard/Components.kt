package com.example.aapdasetu.ui.view.onBoard

import android.content.Context
import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aapdasetu.R
import com.example.aapdasetu.ui.view.APText
import com.example.aapdasetu.ui.view.Button
import com.example.aapdasetu.ui.view.CheckBox
import com.example.aapdasetu.ui.view.ClickableText
import com.example.aapdasetu.ui.view.LogoBox
import com.example.aapdasetu.ui.view.PasswordField
import com.example.aapdasetu.ui.view.ProfileIcon
import com.example.aapdasetu.ui.view.UserInputField
import com.example.aapdasetu.viewmodel.OnboardViewModel

@Composable
fun HeaderSection() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        APText("AapdaSetu", FontWeight.Bold, 48)
        Spacer(Modifier.width(16.dp))
        LogoBox(painterResource(id = R.drawable.logo), "", Modifier.size(96.dp))
    }
    Spacer(Modifier.height(8.dp))
    APText("Register", FontWeight.SemiBold, 40)
}

@Composable
fun ProfileImageSection(a : Painter, viewModel: OnboardViewModel, galleryLauncher: ManagedActivityResultLauncher<String, Uri?>) {
    Box(modifier = Modifier.padding(horizontal = 96.dp)) {
        if (viewModel.imageUri.collectAsState().value != null) {
            ProfileIcon(2, a, 200) { galleryLauncher.launch("image/*") }
        }
        else {
            ProfileIcon(2, painterResource(id = R.drawable.profile), 200) { galleryLauncher.launch("image/*") }
        }
    }
}

@Composable
fun InputFields(viewModel: OnboardViewModel) {
    UserInputField("Name", "K.K.Pant", Icons.Default.Person, 72) { viewModel.name.value = it }
    UserInputField("Email", "kkpant@iitr.ac.in", Icons.Default.Email, 72) { viewModel.email.value = it }
    UserInputField("Phone No.", "6969696969", Icons.Default.Phone, 72) { viewModel.phone.value = it }
    PasswordField { viewModel.password.value = it }
}

@Composable
fun AgreementRow(viewModel: OnboardViewModel) {
    val agreementChecked by viewModel.agreeTerms.collectAsState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(end = 24.dp)
    ) {
        CheckBox(agreementChecked) { viewModel.agreeTerms.value = it }
        ClickableText("I Agree to the ", "User Agreement", Color(0xFFFC8414), Modifier) {
            viewModel.showDialog.value = true
        }
    }
}

@Composable
fun RegisterButton(viewModel: OnboardViewModel, context: Context, onRegisterSuccess: () -> Unit) {
    Button(
        Modifier.padding(8.dp).fillMaxWidth().height(64.dp),
        "REGISTER",
        FontWeight.Normal,
        24,
    ) { viewModel.registerUser(context, onRegisterSuccess) }
}
