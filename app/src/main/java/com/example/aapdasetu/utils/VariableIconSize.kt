package com.example.aapdasetu.utils

fun variableIconSize(zoom: Float): Float {
    val minZoom = 8f
    val maxZoom = 20f
    val zoomOut = 48f
    val zoomIn = 24f

    return when {
        zoom <= minZoom -> zoomOut
        zoom >= maxZoom -> zoomIn
        else -> zoomOut + (zoom - minZoom) / (maxZoom - minZoom) * (zoomIn - zoomOut)
    }
}
