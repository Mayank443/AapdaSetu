package com.example.aapdasetu.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aapdasetu.viewmodel.MainViewModel

//I had included ViewModel in mainActivity to
// decide app should proceed to UI or close
// and rendering the UI & Composables


class MainActivity : FragmentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Here I initialize ViewModel and trigger permission check
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.checkAndRequestPermissions(this)

        // Observe permission grant result from the Viewmodel
        viewModel.permissionsGranted.observe(this) { granted ->
            if (granted) {
                // If permission granted then proceed with UI
                setContent {
                    AapdaSetu()
                }
            }
        }

        // If permission granted then Exit App
        viewModel.permissionDenied.observe(this) { denied ->
            if (denied) {
                finish()
            }
        }
    }

    // Fun for permission result handling to ViewModel
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        viewModel.onRequestPermissionsResult(this, requestCode, grantResults)
    }
}