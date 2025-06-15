package com.example.aapdasetu.Navigation


import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aapdasetu.ui.view.homeScreen.HomeScreen
import com.example.aapdasetu.ui.view.login.LoginScreen
import com.example.aapdasetu.ui.view.onBoard.OnboardScreen
import com.example.aapdasetu.ui.view.profile.ProfileScreen
import com.example.aapdasetu.ui.view.report.ReportScreen

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen ("Register")
    object Home : Screen("home")
    object Profile : Screen("profile")
    object Report : Screen("report")
}

@SuppressLint("ContextCastToActivity")
@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.Register.route) {
        composable(Screen.Register.route) {
            OnboardScreen(
                onRegisterSuccess = { navController.navigate(Screen.Home.route) },
                onLogin = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {navController.navigate(Screen.Home.route)},
                onRegister = {navController.navigate(Screen.Register.route)}
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                onProfileClick = { navController.navigate(Screen.Profile.route) },
                onReportClick = { navController.navigate(Screen.Report.route) }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Report.route) {
            ReportScreen(
                activity = LocalContext.current as FragmentActivity,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
