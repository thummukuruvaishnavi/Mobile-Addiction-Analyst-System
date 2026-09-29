package com.example.mobileaddictionanalystsystem.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.example.mobileaddictionanalystsystem.NotificationHelper
import com.example.mobileaddictionanalystsystem.data.AppUsage
import com.example.mobileaddictionanalystsystem.data.AppUsageTracker
import com.example.mobileaddictionanalystsystem.data.UsageTracker
import com.example.mobileaddictionanalystsystem.utils.AddictionCalculator


@Composable
fun DashboardScreen(
    onNavigate: (String) -> Unit
) {

    val context = LocalContext.current

    var screenTime by remember {
        mutableStateOf("0h 0m")
    }

    var topApps by remember {
        mutableStateOf(emptyList<AppUsage>())
    }


    // --------------------------------
    // GET TODAY'S USAGE
    // --------------------------------

    LaunchedEffect(Unit) {

        val usage =
            UsageTracker.getTodayScreenTime(context)

        screenTime =
            UsageTracker.formatTime(usage)

        topApps =
            AppUsageTracker.getTopApps(context)
    }


    val risk =
        AddictionCalculator.calculateRisk(screenTime)


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {


        // --------------------------------
        // HEADER
        // --------------------------------

        item {

            Text(
                text = "Mobile Addiction Analyst",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "Today's Usage Dashboard"
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // USAGE ACCESS

            Button(
                onClick = {

                    context.startActivity(
                        Intent(
                            Settings.ACTION_USAGE_ACCESS_SETTINGS
                        )
                    )

                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("Enable Usage Access")
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }


        // --------------------------------
        // SCREEN TIME
        // --------------------------------

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Screen Time",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = screenTime,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )
        }


        // --------------------------------
        // ADDICTION RISK
        // --------------------------------

        item {

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Addiction Risk",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )

                    Text(
                        text = risk,
                        style = MaterialTheme.typography.headlineMedium
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(15.dp)
            )


            // ANALYTICS

            Button(
                onClick = {
                    onNavigate("analytics")
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("📊 View Analytics")
            }


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // HISTORY

            Button(
                onClick = {
                    onNavigate("history")
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("📅 View History")
            }


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // PROFILE

            Button(
                onClick = {
                    onNavigate("profile")
                },
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("👤 Profile")
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Text(
                text = "Top Used Apps",
                style = MaterialTheme.typography.titleLarge
            )


            Spacer(
                modifier = Modifier.height(10.dp)
            )
        }


        // --------------------------------
        // TOP USED APPS
        // --------------------------------

        items(topApps) { app ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),

                    horizontalArrangement =
                        Arrangement.SpaceBetween
                ) {

                    Text(
                        text = app.appName
                    )

                    Text(
                        text =
                            AppUsageTracker.formatUsage(
                                app.usageTime
                            )
                    )
                }
            }
        }


        // --------------------------------
        // AI RECOMMENDATION
        // --------------------------------

        item {

            Spacer(
                modifier = Modifier.height(20.dp)
            )


            Card(
                modifier = Modifier.fillMaxWidth()
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "AI Recommendation",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(
                        modifier = Modifier.height(10.dp)
                    )


                    Text(

                        text = when (risk) {

                            "LOW" ->
                                "Your screen time is under control. Keep maintaining healthy phone usage."

                            "MODERATE" ->
                                "Try reducing unnecessary social media and gaming time."

                            else ->
                                "Your screen time is high. Take regular breaks and reduce unnecessary phone usage."
                        }
                    )
                }
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )


            // --------------------------------
            // NOTIFICATION
            // --------------------------------

            Button(

                onClick = {

                    // Android 13+
                    if (
                        Build.VERSION.SDK_INT >=
                        Build.VERSION_CODES.TIRAMISU
                    ) {

                        val permission =
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            )


                        if (
                            permission !=
                            PackageManager.PERMISSION_GRANTED
                        ) {

                            ActivityCompat.requestPermissions(

                                context as Activity,

                                arrayOf(
                                    Manifest.permission.POST_NOTIFICATIONS
                                ),

                                100
                            )

                        } else {

                            NotificationHelper
                                .showUsageNotification(
                                    context = context,
                                    screenTime = screenTime,
                                    risk = risk
                                )
                        }


                    } else {

                        NotificationHelper
                            .showUsageNotification(
                                context = context,
                                screenTime = screenTime,
                                risk = risk
                            )
                    }
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text(
                    "🔔 Test Usage Notification"
                )
            }


            Spacer(
                modifier = Modifier.height(10.dp)
            )


            // --------------------------------
            // LOGOUT
            // --------------------------------

            OutlinedButton(

                onClick = {
                    onNavigate("login")
                },

                modifier = Modifier.fillMaxWidth()

            ) {

                Text("LOGOUT")
            }


            Spacer(
                modifier = Modifier.height(20.dp)
            )
        }
    }
}