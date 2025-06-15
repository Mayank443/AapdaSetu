package com.example.aapdasetu.utils

import com.google.firebase.Timestamp

fun formatTimestamp(timestamp: Timestamp?): String {
    return timestamp?.toDate()?.let {
        java.text.SimpleDateFormat("dd MMM yyyy, hh:mm a").format(it)
    } ?: "N/A"
}