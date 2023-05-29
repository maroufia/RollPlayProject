package com.example.rollplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.Objects;

public class Settings extends AppCompatActivity {

    SwitchCompat dark_light;
    static  final  String PREF_NAME = "MyAppPrefs";
    static  final  String DARK_MODE_KEY = "darkMode";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_settings);
        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        boolean darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        dark_light = findViewById(R.id.dark_light);
        dark_light.setChecked(darkModeEnabled);


        dark_light.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (isChecked) {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                editor.putBoolean(DARK_MODE_KEY, true);

            }else {

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                editor.putBoolean(DARK_MODE_KEY, false);
            }
            editor.apply();
        });
}


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(Settings.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
