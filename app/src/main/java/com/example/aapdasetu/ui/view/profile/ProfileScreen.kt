package com.example.aapdasetu.ui.view.profile

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.aapdasetu.R
import com.example.aapdasetu.ui.view.Button
import com.example.aapdasetu.ui.view.ProfileIcon
import com.example.aapdasetu.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    onBack: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {

    val user by viewModel.user.collectAsState()
    val profileImageUri by viewModel.profileImageUri.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadUser()
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            viewModel.uploadProfileImage(
                uri = it,
                onSuccess = { Toast.makeText(context, "Image Updated", Toast.LENGTH_SHORT).show() },
                onError = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }
            )
        }
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier.padding(padding).fillMaxSize().padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderSection(onBack)

            val imagePainter = rememberAsyncImagePainter(
                model = profileImageUri ?: user.profileImage ?: R.drawable.profile
            )
            ProfileIcon(2, imagePainter, 200) {
                galleryLauncher.launch("image/*")
            }


            InputFields(viewModel)

            Button(Modifier.padding(8.dp).fillMaxWidth().height(56.dp), "Save", FontWeight.Normal, 24)
            {
                viewModel.updateUser(
                    { Toast.makeText(context, "Profile updated", Toast.LENGTH_SHORT).show()
                        onBack()
                    },
                    {
                        Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}
