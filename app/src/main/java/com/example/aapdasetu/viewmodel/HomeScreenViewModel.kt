package com.example.aapdasetu.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.aapdasetu.data.model.HomeDisasterReport
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeScreenViewModel : ViewModel() {

    var reports by mutableStateOf<List<HomeDisasterReport>>(emptyList())
        private set

    var selectedReport by mutableStateOf<HomeDisasterReport?>(null)
      //  private set

    var profileUrl by mutableStateOf<String?>(null)
        private set

    var lastKnownLocation by mutableStateOf<LatLng?>(null)
        private set

    private val db = FirebaseFirestore.getInstance()

    fun loadReports() {
        db.collection("disasterReports")
            .get()
            .addOnSuccessListener { result ->
                reports = result.documents.mapNotNull { it.toObject(HomeDisasterReport::class.java) }
            }
    }

    fun fetchProfileUrl() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        db.collection("users")
            .document(uid)
            .get()
            .addOnSuccessListener { doc ->
                profileUrl = doc.getString("profileImage")
            }
    }

    fun SelectReport(report: HomeDisasterReport?) {
        selectedReport = report
    }

    fun updateLocation(location: LatLng?) {
        lastKnownLocation = location
    }
}
