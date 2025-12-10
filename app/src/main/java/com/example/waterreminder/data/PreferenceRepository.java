package com.example.waterreminder.data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Simple preference storage for user settings.
 */
public class PreferenceRepository {
    private static final String PREF_NAME = "water_pref";
    private static final String KEY_TARGET = "daily_target";
    private static final String KEY_INTERVAL = "interval_minutes";
    private static final String KEY_START_MIN = "start_minutes";
    private static final String KEY_END_MIN = "end_minutes";

    private static final int DEFAULT_TARGET = 2000;
    private static final int DEFAULT_INTERVAL = 60;
    private static final int DEFAULT_START_MIN = 8 * 60;
    private static final int DEFAULT_END_MIN = 22 * 60;

    private final SharedPreferences prefs;

    public PreferenceRepository(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public int getDailyTarget() {
        return prefs.getInt(KEY_TARGET, DEFAULT_TARGET);
    }

    public void setDailyTarget(int target) {
        prefs.edit().putInt(KEY_TARGET, target).apply();
    }

    public int getIntervalMinutes() {
        return prefs.getInt(KEY_INTERVAL, DEFAULT_INTERVAL);
    }

    public void setIntervalMinutes(int minutes) {
        prefs.edit().putInt(KEY_INTERVAL, minutes).apply();
    }

    public int getStartMinutes() {
        return prefs.getInt(KEY_START_MIN, DEFAULT_START_MIN);
    }

    public int getEndMinutes() {
        return prefs.getInt(KEY_END_MIN, DEFAULT_END_MIN);
    }

    public void setStartEndMinutes(int startMinutes, int endMinutes) {
        prefs.edit()
                .putInt(KEY_START_MIN, startMinutes)
                .putInt(KEY_END_MIN, endMinutes)
                .apply();
    }
}



