package com.example.aapdasetu.data.model

import com.google.firebase.Timestamp

data class HomeDisasterReport(
    val disaster: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val userId: String = "",
    val imageUrls: List<String> = emptyList(),
    val timestamp: Timestamp? = null
)
