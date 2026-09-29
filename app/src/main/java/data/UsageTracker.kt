package com.example.mobileaddictionanalystsystem.data

import android.app.usage.UsageStatsManager
import android.content.Context
import java.util.Calendar

object UsageTracker {

    fun getTodayScreenTime(context: Context): Long {

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

        return stats.sumOf {
            it.totalTimeInForeground
        }
    }

    fun formatTime(milliseconds: Long): String {

        val totalMinutes =
            milliseconds / (1000 * 60)

        val hours =
            totalMinutes / 60

        val minutes =
            totalMinutes % 60

        return "${hours}h ${minutes}m"
    }
}