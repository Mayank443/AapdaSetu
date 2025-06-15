package com.example.aapdasetu.viewmodel

import android.app.Application
import android.net.Uri
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.aapdasetu.data.model.DisasterReport
import com.example.aapdasetu.utils.BiometricUtils
import com.example.aapdasetu.utils.FirebaseUploader
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReportViewModel(application: Application) : AndroidViewModel(application) {

    private val context = application.applicationContext

    private val _disasterType = MutableStateFlow("")
    val disasterType: StateFlow<String> = _disasterType

    private val _customDisaster = MutableStateFlow("")
    val customDisaster: StateFlow<String> = _customDisaster

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _imageUris = MutableStateFlow<List<Uri>>(emptyList())
    val imageUris: StateFlow<List<Uri>> = _imageUris

    private val _isAuthenticated = MutableStateFlow(false)
    val isAuthenticated: StateFlow<Boolean> = _isAuthenticated

    val disasterTypes = listOf("Earthquake", "Landslide", "Fire", "Custom")

    fun onDisasterSelected(value: String) {
        _disasterType.value = value
    }

    fun onCustomDisasterChanged(value: String) {
        _customDisaster.value = value
    }

    fun onDescriptionChanged(value: String) {
        _description.value = value
    }

    fun addImage(uri: Uri) {
        if (_imageUris.value.size >= 3) {
            Toast.makeText(context, "Max 3 images allowed", Toast.LENGTH_SHORT).show()
            return
        }

        val inputStream = context.contentResolver.openInputStream(uri)
        val size = inputStream?.available() ?: 0
        if (size > 500_000) {
            Toast.makeText(context, "Image size exceeds 500KB", Toast.LENGTH_SHORT).show()
            return
        }

        _imageUris.value = _imageUris.value + uri
    }

    fun canSubmit(): Boolean {
        return disasterType.value.isNotBlank() && description.value.isNotBlank() && imageUris.value.isNotEmpty()
    }

    fun authenticate(activity: androidx.fragment.app.FragmentActivity, onSuccess: () -> Unit) {
        val manager = BiometricManager.from(context)
        BiometricUtils.authenticateUser(
            manager,
            activity,
            onSuccess = {
                _isAuthenticated.value = true
                uploadReport()
                onSuccess()
            },
            onError = {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        )
    }

    private fun uploadReport() {
        val report = DisasterReport(
            disasterType = if (_disasterType.value == "Custom") _customDisaster.value else _disasterType.value,
            description = _description.value,
            imageUris = _imageUris.value
        )

        viewModelScope.launch {
            FirebaseUploader.uploadReport(context, report)
        }
    }

    fun reset() {
        _disasterType.value = ""
        _customDisaster.value = ""
        _description.value = ""
        _imageUris.value = emptyList()
        _isAuthenticated.value = false
    }
}
