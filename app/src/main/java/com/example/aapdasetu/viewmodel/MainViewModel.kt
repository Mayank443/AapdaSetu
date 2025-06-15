package com.example.aapdasetu.viewmodel

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aapdasetu.utils.PermissionUtils

class MainViewModel : ViewModel() {

    // LiveData to notify all required permissions are granted or not
    private val _permissionsGranted = MutableLiveData(false)
    val permissionsGranted: LiveData<Boolean> = _permissionsGranted

    // LiveData to notify if the permision are denied
    private val _permissionDenied = MutableLiveData(false)
    val permissionDenied: LiveData<Boolean> = _permissionDenied

    // Request code used to identify the permission
    private val locationCode = 1001

    // Check for location permissions and request them if not granted
    fun checkAndRequestPermissions(activity: Activity) {
        if (PermissionUtils.hasLocationPermissions(activity)) {
            _permissionsGranted.value = true
        } else {
            PermissionUtils.requestLocationPermissions(activity, locationCode)
        }
    }

    // Handle the result of the permission request
    fun onRequestPermissionsResult(activity: Activity, requestCode: Int, grantResults: IntArray) {
        if (requestCode == locationCode) {
            if (PermissionUtils.allPermissionsGranted(grantResults)) {
                _permissionsGranted.value = true
            } else {
                Toast.makeText(activity, "Location permission is required", Toast.LENGTH_LONG).show()
                _permissionDenied.value = true
            }
        }
    }
}
