# 📱 Mobile Addiction Analyst System

## 📌 Project Overview

The **Mobile Addiction Analyst System** is an Android application designed to analyze mobile phone usage and help users understand their digital habits.

The application tracks screen time and application usage, analyzes usage patterns, provides addiction-risk information, and gives recommendations to encourage healthier mobile usage.

---

## 🎯 Objectives

- Monitor daily mobile screen time.
- Track application usage.
- Analyze social media and gaming usage.
- Identify mobile addiction risk levels.
- Provide personalized recommendations.
- Send notifications to remind users about excessive usage.
- Help users develop healthier digital habits.

---

## ✨ Features

### 🔐 User Login
- Simple and user-friendly login interface.
- User profile management.

### 📊 Dashboard
- Displays today's screen time.
- Shows usage statistics.
- Displays addiction risk information.
- Shows frequently used applications.

### 📱 Application Usage Tracking
- Tracks application usage using Android Usage Statistics.
- Identifies applications with high usage.
- Displays top-used applications.

### 🧠 Addiction Risk Analysis
- Analyzes mobile usage patterns.
- Provides an addiction-risk indication.
- Helps users understand their usage behavior.

### 🤖 AI Recommendations
- Provides recommendations based on mobile usage.
- Encourages users to reduce excessive screen time.

### 🔔 Notifications
- Provides usage-related notifications.
- Helps users become aware of excessive mobile usage.

### 👤 Profile
- Displays user profile information.
- Provides a simple profile interface.

---

## 🛠️ Technologies Used

- **Android Studio**
- **Kotlin**
- **Jetpack Compose**
- **Android UsageStatsManager**
- **SQLite**
- **Gradle**
- **Material Design**

---

## 🏗️ Project Structure

```text
Mobile-Addiction-Analyst-System/
│
├── app/
│   └── src/
│       └── main/
│           ├── java/
│           │   └── com.example.mobileaddictionanalystsystem/
│           │       ├── MainActivity.kt
│           │       ├── Navigation.kt
│           │       ├── LoginScreen.kt
│           │       ├── DashboardScreen.kt
│           │       ├── ProfileScreen.kt
│           │       ├── UsageTracker.kt
│           │       ├── NotificationHelper.kt
│           │       └── ...
│           │
│           └── AndroidManifest.xml
│
├── gradle/
├── .gitignore
├── build.gradle.kts
├── gradle.properties
├── gradlew
├── gradlew.bat
└── settings.gradle.kts
