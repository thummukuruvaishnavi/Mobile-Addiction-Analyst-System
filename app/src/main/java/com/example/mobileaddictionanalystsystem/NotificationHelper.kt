package com.example.mobileaddictionanalystsystem

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

object NotificationHelper {

    private const val CHANNEL_ID = "mobile_addiction_channel"
    private const val CHANNEL_NAME = "Mobile Usage Alerts"
    private const val NOTIFICATION_ID = 999

    fun showUsageNotification(
        context: Context,
        screenTime: String,
        risk: String
    ) {

        // Check notification permission on Android 13+
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        // Create notification channel
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.description =
                "Mobile usage and addiction risk notifications"

            val manager =
                context.getSystemService(
                    Context.NOTIFICATION_SERVICE
                ) as NotificationManager

            manager.createNotificationChannel(channel)
        }

        // Notification message
        val message = when (risk) {

            "LOW" ->
                "Your screen time is $screenTime. Keep maintaining healthy phone usage."

            "MODERATE" ->
                "Your screen time is $screenTime. Consider taking a break from your phone."

            else ->
                "Your screen time is $screenTime. Your usage is high. Please take a break."
        }

        // Create notification
        val notification =
            NotificationCompat.Builder(
                context,
                CHANNEL_ID
            )
                .setSmallIcon(
                    android.R.drawable.ic_dialog_info
                )
                .setContentTitle(
                    "Mobile Addiction Analyst"
                )
                .setContentText(
                    message
                )
                .setStyle(
                    NotificationCompat.BigTextStyle()
                        .bigText(message)
                )
                .setPriority(
                    NotificationCompat.PRIORITY_HIGH
                )
                .setAutoCancel(true)
                .build()

        // Show notification
        NotificationManagerCompat
            .from(context)
            .notify(
                NOTIFICATION_ID,
                notification
            )
    }
}