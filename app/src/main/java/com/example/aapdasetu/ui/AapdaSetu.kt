package com.example.aapdasetu.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.aapdasetu.Navigation.AppNavigation

@Composable
fun AapdaSetu() {
    val navController = rememberNavController()
    AppNavigation(navController = navController)
}
