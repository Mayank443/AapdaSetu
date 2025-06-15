package com.example.aapdasetu.ui.view.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.example.aapdasetu.ui.view.LogoBox
import com.example.aapdasetu.ui.view.UserDataField
import com.example.aapdasetu.viewmodel.ProfileViewModel

@Composable
fun HeaderSection(a: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .background(Color.Transparent),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = {a()}
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                contentDescription = "",
                modifier = Modifier.size(80.dp)
            )
        }
        APText("Profile", FontWeight.Bold, 32)
        LogoBox(painterResource(id = R.drawable.logo), "", Modifier.size(64.dp))
    }
    Spacer(Modifier.height(32.dp))
}


@Composable
fun InputFields(viewModel:ProfileViewModel ) {

    val user by viewModel.user.collectAsState()

    Spacer(Modifier.height(24.dp))
    UserDataField(user.name, "Name", Icons.Default.Person, viewModel::onNameChange)
    UserDataField(user.email, "Email", Icons.Default.Email, viewModel::onEmailChange)
    UserDataField(user.phone, "Phone No.", Icons.Default.Phone, viewModel::onPhoneChange)
    Spacer(Modifier.height(24.dp))
}