package com.example.waterreminder.reminder;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.waterreminder.data.PreferenceRepository;
import com.example.waterreminder.data.IntakeRepository;

import java.util.Calendar;

public class ReminderWorker extends Worker {
    public ReminderWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Context ctx = getApplicationContext();
        PreferenceRepository pref = new PreferenceRepository(ctx);
        if (!isWithinWindow(pref)) {
            return Result.success();
        }
        NotificationHelper.showReminder(ctx);
        // Optionally preload total to warm Room
        new IntakeRepository(ctx).getTodayTotal();
        return Result.success();
    }

    private boolean isWithinWindow(PreferenceRepository pref) {
        int start = pref.getStartMinutes();
        int end = pref.getEndMinutes();
        Calendar cal = Calendar.getInstance();
        int minutes = cal.get(Calendar.HOUR_OF_DAY) * 60 + cal.get(Calendar.MINUTE);
        return minutes >= start && minutes <= end;
    }
}



