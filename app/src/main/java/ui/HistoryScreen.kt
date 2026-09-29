package com.example.mobileaddictionanalystsystem.ui

import android.app.usage.UsageStatsManager
import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.mobileaddictionanalystsystem.data.UsageTracker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun HistoryScreen(
    onBack: () -> Unit
) {

    val context = LocalContext.current

    var todayUsage by remember {
        mutableStateOf("0h 0m")
    }

    var yesterdayUsage by remember {
        mutableStateOf("0h 0m")
    }

    LaunchedEffect(Unit) {

        todayUsage = getUsageForDay(
            context = context,
            daysAgo = 0
        )

        yesterdayUsage = getUsageForDay(
            context = context,
            daysAgo = 1
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),

        verticalArrangement = Arrangement.Top
    ) {

        Text(
            text = "Usage History",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        HistoryCard(
            title = "Today",
            usage = todayUsage
        )

        HistoryCard(
            title = "Yesterday",
            usage = yesterdayUsage
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Text(
            text = "This section shows your daily mobile screen-time usage.",
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(
            modifier = Modifier.height(30.dp)
        )

        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Back to Dashboard")
        }
    }
}

@Composable
fun HistoryCard(
    title: String,
    usage: String
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Column(
            modifier = Modifier.padding(20.dp)
        ) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Screen Time: $usage",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun getUsageForDay(
    context: Context,
    daysAgo: Int
): String {

    val usageStatsManager =
        context.getSystemService(
            Context.USAGE_STATS_SERVICE
        ) as UsageStatsManager

    val calendar = Calendar.getInstance()

    calendar.add(
        Calendar.DAY_OF_YEAR,
        -daysAgo
    )

    calendar.set(
        Calendar.HOUR_OF_DAY,
        0
    )

    calendar.set(
        Calendar.MINUTE,
        0
    )

    calendar.set(
        Calendar.SECOND,
        0
    )

    calendar.set(
        Calendar.MILLISECOND,
        0
    )

    val startTime = calendar.timeInMillis

    val endCalendar =
        calendar.clone() as Calendar

    endCalendar.add(
        Calendar.DAY_OF_YEAR,
        1
    )

    val endTime =
        endCalendar.timeInMillis

    val stats =
        usageStatsManager.queryUsageStats(
            UsageStatsManager.INTERVAL_DAILY,
            startTime,
            endTime
        )

    val totalUsage =
        stats.sumOf {
            it.totalTimeInForeground
        }

    return UsageTracker.formatTime(
        totalUsage
    )
}