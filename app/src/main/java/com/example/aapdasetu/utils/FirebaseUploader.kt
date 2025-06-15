
package com.example.aapdasetu.utils

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.annotation.RequiresPermission
import com.example.aapdasetu.data.model.DisasterReport
import com.google.android.gms.location.LocationServices
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

object FirebaseUploader {

    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    suspend fun uploadReport(context: Context, report: DisasterReport) {
        val user = FirebaseAuth.getInstance().currentUser ?: run {
            Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        val location = locationClient.lastLocation.await() ?: run {
            Toast.makeText(context, "Unable to get location", Toast.LENGTH_SHORT).show()
            return
        }

        val firestore = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()

        val reportData = hashMapOf(
            "disaster" to report.disasterType,
            "description" to report.description,
            "latitude" to location.latitude,
            "longitude" to location.longitude,
            "userId" to user.uid,
            "timestamp" to Timestamp.now()
        )

        val docRef = firestore.collection("disasterReports").add(reportData).await()
        val storageRef = storage.reference.child("reports/${docRef.id}")
        val imageUrls = mutableListOf<String>()

        report.imageUris.forEachIndexed { index, uri ->
            val imageRef = storageRef.child("image_$index.jpg")
            imageRef.putFile(uri).await()
            val url = imageRef.downloadUrl.await().toString()
            imageUrls.add(url)
        }

        docRef.update("imageUrls", imageUrls).addOnSuccessListener {
            Toast.makeText(context, "✅ Report submitted", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to upload image URLs", Toast.LENGTH_SHORT).show()
        }
    }
}
