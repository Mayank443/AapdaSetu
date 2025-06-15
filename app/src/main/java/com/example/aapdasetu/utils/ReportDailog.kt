package com.example.aapdasetu.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.aapdasetu.data.model.HomeDisasterReport
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun ReportDialog(
    report: HomeDisasterReport,
    onDismiss: () -> Unit
) {
    val imageCount = report.imageUrls.size
    var currentImageIndex by remember { mutableIntStateOf(0) }

    var reporterName by remember { mutableStateOf("Loading...") }

    LaunchedEffect(report.userId) {
        FirebaseFirestore.getInstance()
            .collection("users")
            .document(report.userId)
            .get()
            .addOnSuccessListener { doc ->
                reporterName = doc.getString("name") ?: "Unknown User"
            }
            .addOnFailureListener {
                reporterName = "Unknown User"
            }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        },
        title = {
            Text("⚠️ ${report.disaster}", fontWeight = FontWeight.Bold)
        },
        text = {
            Column {
                Text("Reported by: $reporterName")
                Text("Time: ${formatTimestamp(report.timestamp)}")
                Text("Description: ${report.description}")
                Spacer(modifier = Modifier.height(8.dp))

                if (imageCount > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        // ⬅️ Left icon (only visible if index > 0)
                        if (currentImageIndex > 0) {
                            IconButton(onClick = {
                                if (currentImageIndex > 0) currentImageIndex--
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                                    contentDescription = "Previous Image"
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp)) // reserve space
                        }

                        // 🖼 Image
                        AsyncImage(
                            model = report.imageUrls[currentImageIndex],
                            contentDescription = null,
                            modifier = Modifier
                                .weight(1f)
                                .height(180.dp)
                                .padding(horizontal = 8.dp)
                        )

                        // ➡️ Right icon (only visible if index < last)
                        if (currentImageIndex < imageCount - 1) {
                            IconButton(onClick = {
                                if (currentImageIndex < imageCount - 1) currentImageIndex++
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                    contentDescription = "Next Image"
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.width(48.dp)) // reserve space
                        }
                    }
                }
            }
        }
    )
}