package com.example.rollplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

public class Settings extends AppCompatActivity {

    SwitchCompat dark_light;
    static  final  String PREF_NAME = "MyAppPrefs";
    static  final  String DARK_MODE_KEY = "darkMode";
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        boolean darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false);

        dark_light = findViewById(R.id.dark_light);
        dark_light.setChecked(darkModeEnabled);

        dark_light.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean(DARK_MODE_KEY, isChecked).apply();
            applyDarkMode(isChecked);
        });

        applyDarkMode(darkModeEnabled);
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        dark_light.setChecked(darkModeEnabled);
        applyDarkMode(darkModeEnabled);
    }

    void applyDarkMode(boolean darkModeEnabled) {
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            setResult(1, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
