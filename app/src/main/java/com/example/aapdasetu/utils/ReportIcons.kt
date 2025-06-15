package com.example.aapdasetu.utils

import com.example.aapdasetu.R

fun reportIcons(type: String): Int {
    return when (type.lowercase()) {
        "fire" -> R.drawable.fire
        "earthquake" -> R.drawable.earthquake
        "landslide" -> R.drawable.landslide
        else -> R.drawable.custom
    }
}