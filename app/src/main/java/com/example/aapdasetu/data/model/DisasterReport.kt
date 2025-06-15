package com.example.aapdasetu.data.model

import android.net.Uri

data class DisasterReport(
    val disasterType: String,
    val description: String,
    val imageUris: List<Uri>
)
