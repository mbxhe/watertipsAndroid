package com.example.waterreminder;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.waterreminder.databinding.ActivityMainBinding;
import com.example.waterreminder.reminder.NotificationHelper;

/**
 * Simple single-activity UI with progress and settings.
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private WaterViewModel viewModel;

    private final ActivityResultLauncher<String> notificationPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
                if (!granted) {
                    Toast.makeText(this, "通知权限被拒绝，提醒将不可用", Toast.LENGTH_SHORT).show();
                } else {
                    NotificationHelper.showReminder(this);
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(WaterViewModel.class);
        observeData();
        bindActions();
        requestNotificationPermissionIfNeeded();
        preloadSettings();
    }

    private void observeData() {
        viewModel.todayTotal().observe(this, total -> updateProgress(total, viewModel.target().getValue()));
        viewModel.target().observe(this, target -> updateProgress(viewModel.todayTotal().getValue(), target));
    }

    private void bindActions() {
        binding.buttonAdd200.setOnClickListener(v -> viewModel.addIntake(200));
        binding.buttonAdd300.setOnClickListener(v -> viewModel.addIntake(300));
        binding.buttonSave.setOnClickListener(v -> saveSettings());
    }

    private void updateProgress(Integer total, Integer target) {
        if (total == null) total = 0;
        if (target == null || target <= 0) target = 1;
        int percent = Math.min(100, (int) ((total * 100f) / target));
        binding.progressBar.setProgress(percent);
        binding.textProgress.setText(total + "/" + target + " ml (" + percent + "%)");
    }

    private void preloadSettings() {
        binding.inputTarget.setText(String.valueOf(viewModel.target().getValue() != null ? viewModel.target().getValue() : 2000));
        binding.inputInterval.setText(String.valueOf(viewModel.getInterval()));
        binding.inputStart.setText(minutesToString(viewModel.getStartMinutes()));
        binding.inputEnd.setText(minutesToString(viewModel.getEndMinutes()));
    }

    private void saveSettings() {
        String targetStr = binding.inputTarget.getText() != null ? binding.inputTarget.getText().toString() : "";
        String intervalStr = binding.inputInterval.getText() != null ? binding.inputInterval.getText().toString() : "";
        String startStr = binding.inputStart.getText() != null ? binding.inputStart.getText().toString() : "";
        String endStr = binding.inputEnd.getText() != null ? binding.inputEnd.getText().toString() : "";

        if (TextUtils.isEmpty(targetStr) || TextUtils.isEmpty(intervalStr)
                || TextUtils.isEmpty(startStr) || TextUtils.isEmpty(endStr)) {
            Toast.makeText(this, "请填写所有设置", Toast.LENGTH_SHORT).show();
            return;
        }

        int target = Integer.parseInt(targetStr);
        int interval = Integer.parseInt(intervalStr);
        int start = parseMinutes(startStr);
        int end = parseMinutes(endStr);

        if (target <= 0 || interval < 15 || start < 0 || end < start) {
            Toast.makeText(this, "设置不合法，间隔至少15分钟", Toast.LENGTH_SHORT).show();
            return;
        }

        viewModel.saveSettings(target, interval, start, end);
        Toast.makeText(this, "已保存并更新提醒", Toast.LENGTH_SHORT).show();
    }

    private int parseMinutes(String hhmm) {
        String[] parts = hhmm.split(":");
        if (parts.length != 2) return 0;
        try {
            int h = Integer.parseInt(parts[0]);
            int m = Integer.parseInt(parts[1]);
            return h * 60 + m;
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String minutesToString(int minutes) {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("%02d:%02d", h, m);
    }

    private void requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}



