package com.example.mobileaddictionanalystsystem.data

import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar

data class AppUsage(
    val appName: String,
    val packageName: String,
    val usageTime: Long
)

object AppUsageTracker {

    fun getTopApps(
        context: Context,
        limit: Int = 5
    ): List<AppUsage> {

        val usageStatsManager =
            context.getSystemService(Context.USAGE_STATS_SERVICE)
                    as UsageStatsManager

        val calendar = Calendar.getInstance()

        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val startTime = calendar.timeInMillis
        val endTime = System.currentTimeMillis()

        val stats = usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

        return stats
            .filter {
                it.totalTimeInForeground > 0
            }
            .groupBy {
                it.packageName
            }
            .map { (packageName, appStats) ->

                val totalUsage = appStats.sumOf {
                    it.totalTimeInForeground
                }

                val appName = getAppName(
                    context,
                    packageName
                )

                AppUsage(
                    appName = appName,
                    packageName = packageName,
                    usageTime = totalUsage
                )
            }
            .sortedByDescending {
                it.usageTime
            }
            .take(limit)
    }

    private fun getAppName(
        context: Context,
        packageName: String
    ): String {

        // Common application names
        return when (packageName) {

            "com.google.android.youtube" ->
                "YouTube"

            "com.android.chrome" ->
                "Chrome"

            "com.whatsapp" ->
                "WhatsApp"

            "com.snapchat.android" ->
                "Snapchat"

            "com.instagram.android" ->
                "Instagram"

            "com.facebook.katana" ->
                "Facebook"

            "com.google.android.gm" ->
                "Gmail"

            "com.google.android.apps.maps" ->
                "Google Maps"

            "com.google.android.googlequicksearchbox" ->
                "Google"

            "com.google.android.apps.photos" ->
                "Google Photos"

            "com.android.vending" ->
                "Google Play Store"

            else -> {
                try {

                    val applicationInfo =
                        context.packageManager
                            .getApplicationInfo(
                                packageName,
                                0
                            )

                    context.packageManager
                        .getApplicationLabel(
                            applicationInfo
                        )
                        .toString()

                } catch (e: Exception) {

                    packageName
                }
            }
        }
    }

    fun formatUsage(milliseconds: Long): String {

        val totalMinutes =
            milliseconds / (1000 * 60)

        val hours =
            totalMinutes / 60

        val minutes =
            totalMinutes % 60

        return "${hours}h ${minutes}m"
    }
}