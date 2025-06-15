package com.example.aapdasetu.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

@SuppressLint("MissingPermission")
fun fetchLastLocation(
    context: Context,
    onLocationResult: (LatLng?) -> Unit
) {
    val locationClient = LocationServices.getFusedLocationProviderClient(context)
    locationClient.lastLocation
        .addOnSuccessListener { location: Location? ->
            onLocationResult(location?.let { LatLng(it.latitude, it.longitude) })
        }
        .addOnFailureListener {
            onLocationResult(null)
        }
}
