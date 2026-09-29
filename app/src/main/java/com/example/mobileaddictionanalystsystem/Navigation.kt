package com.example.mobileaddictionanalystsystem.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.mobileaddictionanalystsystem.ui.AnalyticsScreen
import com.example.mobileaddictionanalystsystem.ui.DashboardScreen
import com.example.mobileaddictionanalystsystem.ui.HistoryScreen
import com.example.mobileaddictionanalystsystem.ui.LoginScreen
import com.example.mobileaddictionanalystsystem.ui.ProfileScreen

@Composable
fun AppNavigation() {

    var currentScreen by remember {
        mutableStateOf("login")
    }

    when (currentScreen) {

        "login" -> {
            LoginScreen {
                currentScreen = "dashboard"
            }
        }

        "dashboard" -> {
            DashboardScreen(
                onNavigate = { screen ->
                    currentScreen = screen
                }
            )
        }

        "analytics" -> {
            AnalyticsScreen(
                onBack = {
                    currentScreen = "dashboard"
                }
            )
        }

        "history" -> {
            HistoryScreen(
                onBack = {
                    currentScreen = "dashboard"
                }
            )
        }

        "profile" -> {
            ProfileScreen(
                onBack = {
                    currentScreen = "dashboard"
                }
            )
        }
    }
}