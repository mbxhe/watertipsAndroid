package com.example.waterreminder;

import android.app.Application;

import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.waterreminder.data.PreferenceRepository;
import com.example.waterreminder.reminder.ReminderWorker;

import java.util.concurrent.TimeUnit;

/**
 * Application entry to initialize periodic work when app launches.
 */
public class WaterReminderApp extends Application {
    public static final String WORK_NAME = "water_reminder_work";

    @Override
    public void onCreate() {
        super.onCreate();
        scheduleReminder();
    }

    private void scheduleReminder() {
        PreferenceRepository repo = new PreferenceRepository(getApplicationContext());
        int interval = repo.getIntervalMinutes(); // fallback to default if empty
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                ReminderWorker.class,
                interval,
                TimeUnit.MINUTES)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );
    }
}



