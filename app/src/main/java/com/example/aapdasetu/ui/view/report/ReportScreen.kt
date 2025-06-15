package com.example.aapdasetu.ui.view.report

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aapdasetu.ui.view.Button
import com.example.aapdasetu.ui.view.UserInputField
import com.example.aapdasetu.viewmodel.ReportViewModel

@Composable
fun ReportScreen(
    activity: FragmentActivity,
    onBack: () -> Unit,
    viewModel: ReportViewModel = viewModel()
) {
    val context = LocalContext.current
    val screenWidthDp = LocalConfiguration.current.screenWidthDp.dp

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let { viewModel.addImage(it) }
    }

    val disasterType by viewModel.disasterType.collectAsState()
    val customDisaster by viewModel.customDisaster.collectAsState()
    val imageUris by viewModel.imageUris.collectAsState()
    val isAuthenticated by viewModel.isAuthenticated.collectAsState()

    Scaffold(
        modifier = Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
        content = { padding ->
            Column(modifier = Modifier.padding(padding)) {

                Header(onBack)

                Spacer(Modifier.height(24.dp))

                DisasterTypeDropdown(
                    items = viewModel.disasterTypes,
                    selected = disasterType,
                    customValue = customDisaster,
                    onSelectedChange = { viewModel.onDisasterSelected(it) },
                    onCustomChange = { viewModel.onCustomDisasterChanged(it) }
                )

                Spacer(Modifier.height(24.dp))

                ImageUploader(
                    imageUris = imageUris,
                    screenWidth = screenWidthDp,
                    launcher = launcher
                )

                Spacer(Modifier.height(16.dp))

                UserInputField(
                    "Disaster Description",
                    "What is the type of disaster?\nWhat is its scale?\nWhat kind of support you need?\netc....",
                    null,
                    240
                )
                {
                    viewModel.onDescriptionChanged(it)
                }

                Spacer(Modifier.height(16.dp))

                Button(Modifier.fillMaxWidth(), "Authenticate & REPORT", FontWeight.Normal, 24) {
                    if (viewModel.canSubmit()) {
                        viewModel.authenticate(activity) {
                            viewModel.reset()
                            onBack()
                        }
                    } else {
                        Toast.makeText(context, "Please complete all fields", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = if (isAuthenticated) "\u2705 Report submitted" else "\u26A0\uFE0F False reporting leads to legal action",
                    color = if (isAuthenticated) MaterialTheme.colorScheme.primary else Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }
        }
    )
}

