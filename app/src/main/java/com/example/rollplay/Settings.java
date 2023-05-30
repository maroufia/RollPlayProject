package com.example.rollplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    SwitchCompat dark_light;
    static  final  String PREF_NAME = "MyAppPrefs";
    static  final  String DARK_MODE_KEY = "darkMode";
    SharedPreferences sharedPreferences;
    ArrayList<String> recentRolls;
    ArrayList<String> recentRollResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recentRolls = bundle.getStringArrayList("Recent Rolls"); // or other values
            recentRollResults = bundle.getStringArrayList("Recent Roll Results");
        }
        else
            recentRolls = null;

        Button history = findViewById(R.id.history);

        View.OnClickListener history_button = v -> {
            Intent intent = new Intent(Settings.this, HistoryActivity.class);
            Bundle b = new Bundle();
            b.putStringArrayList("Recent Rolls", recentRolls);
            b.putStringArrayList("Recent Roll Results", recentRollResults);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        };

        history.setOnClickListener(history_button);

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
            Bundle b = new Bundle();
            b.putStringArrayList("Recent Rolls", recentRolls);
            b.putStringArrayList("Recent Roll Results", recentRollResults);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
