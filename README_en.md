# WaterReminder Guide

## Overview

WaterReminder lets you set a daily hydration goal, schedule reminder intervals within a time window, and log water quickly with 200ml/300ml buttons. The app shows your progress against the goal and sends local notifications during the active window.

## Key Features

- Quick logging: tap 200ml or 300ml buttons to add to today’s total.
- Progress view: progress bar and text show intake vs. target.
- Custom reminders: configure daily target, interval, start/end time; interval must be at least 15 minutes.
- Background notifications: periodic local notifications via WorkManager (Android 13+ needs notification permission).

## Install & Run

1. Open the repo root `watertipsAndroid` in Android Studio.
2. Sync Gradle and connect a device or emulator (API 26+ recommended).
3. Run the `app` module to view the main screen and test reminders.

## How to Use

1. Defaults: daily target 2000ml, interval 60 minutes, window 08:00–22:00.
2. Enter a new target (ml), interval (minutes), and start/end times, then tap “Save” to apply settings and reschedule reminders.
3. After drinking, tap 200ml or 300ml to record; the progress updates immediately.
4. Tapping a reminder takes you back to the main screen.

## Permissions & Data

- Notifications: on Android 13+, grant notification permission at first launch to receive reminders.
- Data storage: intake records live in a local Room DB; settings live in SharedPreferences and are removed on uninstall.

## Troubleshooting

- Not receiving reminders: ensure notification permission is granted and the current time is within the configured start/end window.
- Interval errors: reminder interval must be 15 minutes or more and provided as an integer.

