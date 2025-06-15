package com.example.aapdasetu.ui.view.homeScreen

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.aapdasetu.R
import com.example.aapdasetu.ui.view.APText
import com.example.aapdasetu.ui.view.Button
import com.example.aapdasetu.ui.view.LogoBox
import com.example.aapdasetu.ui.view.ProfileIcon
import com.example.aapdasetu.utils.ReportDialog
import com.example.aapdasetu.utils.bitmapDescriptorFromVector
import com.example.aapdasetu.utils.reportIcons
import com.example.aapdasetu.utils.fetchLastLocation
import com.example.aapdasetu.utils.variableIconSize
import com.example.aapdasetu.viewmodel.HomeScreenViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun HomeScreen(
    onProfileClick: () -> Unit,
    onReportClick: () -> Unit,
    viewModel: HomeScreenViewModel = viewModel()
) {
    val context = LocalContext.current
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(20.5937, 78.9629), 5f)
    }

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions.all { it.value }) {
            fetchLastLocation(context) {
                viewModel.updateLocation(it)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadReports()
        viewModel.fetchProfileUrl()
        launcher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ))
    }

    Scaffold { padding ->
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState
            ) {

                val zoom = cameraPositionState.position.zoom

                val sizeInDp = variableIconSize(zoom)

                viewModel.reports.forEach { report ->
                    Marker(
                        state = MarkerState(LatLng(report.latitude, report.longitude)),
                        icon = bitmapDescriptorFromVector(context, reportIcons(report.disaster),sizeInDp),
                        onClick = {
                            viewModel.SelectReport(report)
                            true
                        }
                    )
                }
            }

            viewModel.selectedReport?.let {
                ReportDialog(it){
                    viewModel.SelectReport(null)
                }
            }

            if (viewModel.lastKnownLocation != null) {
                IconButton(
                    onClick = {
                        cameraPositionState.move(
                            CameraUpdateFactory.newLatLngZoom(viewModel.lastKnownLocation!!, 15f)
                        )
                    },
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                        .background(Color.White, shape = CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = "My Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 32.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    LogoBox(painterResource(id = R.drawable.logo), "", Modifier.size(64.dp))
                    APText("AapdaSetu", FontWeight.Bold, 32)
                    ProfileIcon(
                        2,
                        rememberAsyncImagePainter(viewModel.profileUrl ?: R.drawable.profile),
                        56,
                    ) {
                        onProfileClick()
                    }
                }
                Button(
                    Modifier.padding(8.dp).height(64.dp),
                    "Report",
                    FontWeight.Normal,
                    24,
                ) {
                    onReportClick()
                }
            }
        }
    }
}

