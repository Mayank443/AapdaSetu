package com.example.aapdasetu.ui.view.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.aapdasetu.R
import com.example.aapdasetu.ui.view.APText
import com.example.aapdasetu.ui.view.CheckBox
import com.example.aapdasetu.ui.view.ClickableText
import com.example.aapdasetu.ui.view.LogoBox
import com.example.aapdasetu.ui.view.PasswordField
import com.example.aapdasetu.ui.view.UserInputField
import com.example.aapdasetu.viewmodel.LoginViewModel

@Composable
fun HeaderSection(){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        APText("AapdaSetu", FontWeight.Bold, 48)
        Spacer(Modifier.width(16.dp))
        LogoBox(painterResource(id = R.drawable.logo), "", Modifier.size(96.dp))
    }
    Spacer(Modifier.height(48.dp))
    APText("Login", FontWeight.SemiBold, 40)
    Spacer(Modifier.height(32.dp))
}

@Composable
fun InputFields(viewModel: LoginViewModel){
    UserInputField("Email", "kkpant@iitr.ac.in", Icons.Default.Email, 72) {
        viewModel.email.value = it
    }
    Spacer(Modifier.height(8.dp))

    PasswordField {
        viewModel.password.value = it
    }

    Spacer(Modifier.height(16.dp))
}

@Composable
fun AgreementRow(viewModel: LoginViewModel) {

    val agreementChecked by viewModel.agreeTerms.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth().padding(end = 24.dp)
    ) {
        CheckBox(agreementChecked) {
            viewModel.agreeTerms.value = it
        }
        ClickableText(
            "I Agree to the ",
            "User Agreement",
            Color(0xFFFC8414),
            Modifier
        ) {
            viewModel.showDialog.value = true
        }
    }

    Spacer(Modifier.height(16.dp))
}