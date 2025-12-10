package com.example.waterreminder.data;

import android.content.Context;

import java.util.Calendar;
import java.util.List;

public class IntakeRepository {
    private final WaterIntakeDao dao;

    public IntakeRepository(Context context) {
        dao = AppDatabase.getInstance(context).waterIntakeDao();
    }

    public void addIntake(int volumeMl) {
        dao.insert(new WaterIntake(System.currentTimeMillis(), volumeMl));
    }

    public int getTodayTotal() {
        long[] range = todayRange();
        Integer value = dao.totalByRange(range[0], range[1]);
        return value == null ? 0 : value;
    }

    public List<WaterIntake> getTodayEntries() {
        long[] range = todayRange();
        return dao.getByRange(range[0], range[1]);
    }

    private long[] todayRange() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long start = cal.getTimeInMillis();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        long end = cal.getTimeInMillis() - 1;
        return new long[]{start, end};
    }
}



