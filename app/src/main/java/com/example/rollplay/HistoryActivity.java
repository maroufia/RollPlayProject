package com.example.rollplay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Objects;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView history1 = findViewById(R.id.roll1);
        TextView history2 = findViewById(R.id.roll2);
        TextView history3 = findViewById(R.id.roll3);
        TextView history4 = findViewById(R.id.roll4);

        final ArrayList<String> recentRolls;
        final ArrayList<String> recentRollResults;
        final ArrayList<String> recentModifiers;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            recentRolls = bundle.getStringArrayList("Recent Rolls"); // or other values
            recentRollResults = bundle.getStringArrayList("Recent Roll Results");
            recentModifiers = bundle.getStringArrayList("Recent Modifiers");
        }
        else {
            recentRolls = null;
            recentRollResults = null;
            recentModifiers = null;
        }

        String history_text;
        if (recentRolls != null)
            switch (recentRolls.size()) {
                case 1:
                    if (recentModifiers.get(0).equals("0")){
                        history_text = recentRolls.get(0) + "\nResult: " + recentRollResults.get(0);
                        history1.setText(history_text);
                    } else {
                        history_text = recentRolls.get(0)  + recentModifiers.get(0) + "\nResult: " + recentRollResults.get(0);
                        history1.setText(history_text);
                    }
                    break;
                case 2:
                    if (recentModifiers.get(1).equals("0")){
                        history_text = recentRolls.get(1) + "\nResult: " + recentRollResults.get(1);
                        history1.setText(history_text);
                    } else {
                        history_text = recentRolls.get(1) + recentModifiers.get(1) + "\nResult: " + recentRollResults.get(1);
                        history1.setText(history_text);
                    }
                    if (recentModifiers.get(0).equals("0")){
                        history_text = recentRolls.get(0) + "\nResult: " + recentRollResults.get(0);
                        history2.setText(history_text);
                    } else {
                        history_text = recentRolls.get(0) + recentModifiers.get(0) + "\nResult: " + recentRollResults.get(0);
                        history2.setText(history_text);
                    }
                    break;
                case 3:
                    if (recentModifiers.get(2).equals("0")){
                        history_text = recentRolls.get(2) + "\nResult: " + recentRollResults.get(2);
                        history1.setText(history_text);
                    } else {
                        history_text = recentRolls.get(2) + recentModifiers.get(2) + "\nResult: " + recentRollResults.get(2);
                        history1.setText(history_text);
                    }
                    if (recentModifiers.get(1).equals("0")){
                        history_text = recentRolls.get(1) + "\nResult: " + recentRollResults.get(1);
                        history2.setText(history_text);
                    } else {
                        history_text = recentRolls.get(1) + recentModifiers.get(1) + "\nResult: " + recentRollResults.get(1);
                        history2.setText(history_text);
                    }
                    if (recentModifiers.get(0).equals("0")){
                        history_text = recentRolls.get(0) + "\nResult: " + recentRollResults.get(0);
                        history3.setText(history_text);
                    } else {
                        history_text = recentRolls.get(0) + recentModifiers.get(0) + "\nResult: " + recentRollResults.get(0);
                        history3.setText(history_text);
                    }
                    break;
                case 4:
                    if (recentModifiers.get(3).equals("0")){
                        history_text = recentRolls.get(3) + "\nResult: " + recentRollResults.get(3);
                        history1.setText(history_text);
                    } else {
                        history_text = recentRolls.get(3) + recentModifiers.get(3) + "\nResult: " + recentRollResults.get(3);
                        history1.setText(history_text);
                    }
                    if (recentModifiers.get(2).equals("0")){
                        history_text = recentRolls.get(2) + "\nResult: " + recentRollResults.get(2);
                        history2.setText(history_text);
                    } else {
                        history_text = recentRolls.get(2) + recentModifiers.get(2) + "\nResult: " + recentRollResults.get(2);
                        history2.setText(history_text);
                    }
                    if (recentModifiers.get(1).equals("0")){
                        history_text = recentRolls.get(1) + "\nResult: " + recentRollResults.get(1);
                        history3.setText(history_text);
                    } else {
                        history_text = recentRolls.get(1) + recentModifiers.get(1) + "\nResult: " + recentRollResults.get(1);
                        history3.setText(history_text);
                    }
                    if (recentModifiers.get(0).equals("0")){
                        history_text = recentRolls.get(0) + "\nResult: " + recentRollResults.get(0);
                        history4.setText(history_text);
                    } else {
                        history_text = recentRolls.get(0) + recentModifiers.get(0) + "\nResult: " + recentRollResults.get(0);
                        history4.setText(history_text);
                    }
                    break;
            }


        Button back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, Settings.class);
            setResult(1, intent);
            finish();
        });

    }

}