package com.example.mobileaddictionanalystsystem.utils

object AddictionCalculator {

    fun calculateRisk(screenTime: String): String {

        val hours = try {
            screenTime
                .substringBefore("h")
                .trim()
                .toDouble()
        } catch (e: Exception) {
            0.0
        }

        return when {
            hours < 3 -> "LOW"
            hours < 6 -> "MODERATE"
            else -> "HIGH"
        }
    }
}