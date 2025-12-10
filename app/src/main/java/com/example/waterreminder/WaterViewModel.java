package com.example.waterreminder;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.waterreminder.data.IntakeRepository;
import com.example.waterreminder.data.PreferenceRepository;
import com.example.waterreminder.reminder.ReminderWorker;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class WaterViewModel extends AndroidViewModel {
    private final IntakeRepository intakeRepo;
    private final PreferenceRepository prefRepo;
    private final MutableLiveData<Integer> todayTotal = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> target = new MutableLiveData<>(0);

    public WaterViewModel(@NonNull Application application) {
        super(application);
        intakeRepo = new IntakeRepository(application);
        prefRepo = new PreferenceRepository(application);
        load();
    }

    private void load() {
        Executors.newSingleThreadExecutor().execute(() -> {
            int total = intakeRepo.getTodayTotal();
            todayTotal.postValue(total);
            target.postValue(prefRepo.getDailyTarget());
        });
    }

    public LiveData<Integer> todayTotal() {
        return todayTotal;
    }

    public LiveData<Integer> target() {
        return target;
    }

    public void addIntake(int volume) {
        Executors.newSingleThreadExecutor().execute(() -> {
            intakeRepo.addIntake(volume);
            int total = intakeRepo.getTodayTotal();
            todayTotal.postValue(total);
        });
    }

    public void saveSettings(int newTarget, int intervalMinutes, int startMinutes, int endMinutes) {
        Executors.newSingleThreadExecutor().execute(() -> {
            prefRepo.setDailyTarget(newTarget);
            prefRepo.setIntervalMinutes(intervalMinutes);
            prefRepo.setStartEndMinutes(startMinutes, endMinutes);
            target.postValue(newTarget);
            scheduleWork(intervalMinutes);
        });
    }

    public int getInterval() {
        return prefRepo.getIntervalMinutes();
    }

    public int getStartMinutes() {
        return prefRepo.getStartMinutes();
    }

    public int getEndMinutes() {
        return prefRepo.getEndMinutes();
    }

    private void scheduleWork(int intervalMinutes) {
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                ReminderWorker.class,
                intervalMinutes,
                TimeUnit.MINUTES).build();
        WorkManager.getInstance(getApplication()).enqueueUniquePeriodicWork(
                WaterReminderApp.WORK_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request
        );
    }
}



