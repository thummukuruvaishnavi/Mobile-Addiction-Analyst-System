package com.example.mobileaddictionanalystsystem

import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.mobileaddictionanalystsystem.data.UsageTracker
import com.example.mobileaddictionanalystsystem.utils.AddictionCalculator

class UsageNotificationWorker(
    context: android.content.Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {

        Log.d("USAGE_WORKER", "Worker started")

        val usage =
            UsageTracker.getTodayScreenTime(applicationContext)

        val screenTime =
            UsageTracker.formatTime(usage)

        val risk =
            AddictionCalculator.calculateRisk(screenTime)

        Log.d(
            "USAGE_WORKER",
            "Screen Time: $screenTime, Risk: $risk"
        )

        NotificationHelper.showUsageNotification(
            context = applicationContext,
            screenTime = screenTime,
            risk = risk
        )

        return Result.success()
    }
}