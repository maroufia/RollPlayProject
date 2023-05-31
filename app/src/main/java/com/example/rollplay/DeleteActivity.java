package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

import java.util.Objects;

public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);



    }
}