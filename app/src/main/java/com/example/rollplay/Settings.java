package com.example.rollplay;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;

import java.util.ArrayList;

public class Settings extends AppCompatActivity {

    private SwitchCompat dark_light;
    static  final  String PREF_NAME = "MyAppPrefs";
    static  final  String DARK_MODE_KEY = "darkMode";
    SharedPreferences sharedPreferences;
    ArrayList<String> recentRolls;
    ArrayList<String> recentRollResults;
    ArrayList<String> recentModifiers;

    private String selected;
    private ArrayList<String> MainText;
    private boolean d4_enabled;
    private boolean d6_enabled;
    private boolean d8_enabled;
    private boolean d10_enabled;
    private boolean d12_enabled;
    private boolean d20_enabled;
    private boolean plus_enabled;
    private boolean minus_enabled;
    private boolean rollsbar_enabled;
    private String rollsbar_text;
    private boolean add_enabled;
    private boolean usesaved_enabled;
    private boolean clearlast_enabled;
    private boolean clearall_enabled;
    private String mainview_text;
    private boolean modifier_enabled;
    private boolean save_enabled;
    private boolean roll_enabled;
    private boolean details_enabled;
    private ArrayList<Integer> d4_rolls;
    private ArrayList<Integer> d6_rolls;
    private ArrayList<Integer> d8_rolls;
    private ArrayList<Integer> d10_rolls;
    private ArrayList<Integer> d12_rolls;
    private ArrayList<Integer> d20_rolls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Bundle bundle = getIntent().getExtras();
        selected = bundle.getString("selected");
        d4_enabled = bundle.getBoolean("d4_enabled");
        d6_enabled = bundle.getBoolean("d6_enabled");
        d8_enabled = bundle.getBoolean("d8_enabled");
        d10_enabled = bundle.getBoolean("d10_enabled");
        d12_enabled = bundle.getBoolean("d12_enabled");
        d20_enabled = bundle.getBoolean("d20_enabled");
        plus_enabled = bundle.getBoolean("plus_enabled");
        minus_enabled = bundle.getBoolean("minus_enabled");
        rollsbar_enabled = bundle.getBoolean("rollsbar_enabled");
        rollsbar_text = bundle.getString("rollsbar_text");
        add_enabled = bundle.getBoolean("add_enabled");
        usesaved_enabled = bundle.getBoolean("usesaved_enabled");
        clearlast_enabled = bundle.getBoolean("clearlast_enabled");
        clearall_enabled = bundle.getBoolean("clearall_enabled");
        mainview_text = bundle.getString("mainview_text");
        modifier_enabled = bundle.getBoolean("modifier_enabled");
        save_enabled = bundle.getBoolean("save_enabled");
        roll_enabled = bundle.getBoolean("roll_enabled");
        details_enabled = bundle.getBoolean("details_enabled");
        d4_rolls = new ArrayList<>(bundle.getIntegerArrayList("d4_rolls"));
        d6_rolls = new ArrayList<>(bundle.getIntegerArrayList("d6_rolls"));
        d8_rolls = new ArrayList<>(bundle.getIntegerArrayList("d8_rolls"));
        d10_rolls = new ArrayList<>(bundle.getIntegerArrayList("d10_rolls"));
        d12_rolls = new ArrayList<>(bundle.getIntegerArrayList("d12_rolls"));
        d20_rolls = new ArrayList<>(bundle.getIntegerArrayList("d20_rolls"));
        MainText = new ArrayList<>(bundle.getStringArrayList("main_text"));
        recentRolls = bundle.getStringArrayList("Recent Rolls");
        recentRollResults = bundle.getStringArrayList("Recent Roll Results");
        recentModifiers = bundle.getStringArrayList("Recent Modifiers");

        ActivityResultLauncher<Intent> arl = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {

                });

        Button history = findViewById(R.id.history);
        Button view_delete = findViewById(R.id.view_delete);

        View.OnClickListener history_button = v -> {
            Intent intent = new Intent(Settings.this, HistoryActivity.class);
            Bundle b = new Bundle();
            b.putStringArrayList("Recent Rolls", recentRolls);
            b.putStringArrayList("Recent Roll Results", recentRollResults);
            b.putStringArrayList("Recent Modifiers", recentModifiers);
            intent.putExtras(b);

            arl.launch(intent);
        };

        history.setOnClickListener(history_button);

        View.OnClickListener view_delete_lstnr = v -> {
            Intent intent = new Intent(Settings.this, DeleteSavedActivity.class);
            arl.launch(intent);
        };

        view_delete.setOnClickListener(view_delete_lstnr);

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
            b.putString("selected", selected);
            b.putBoolean("d4_enabled", d4_enabled);
            b.putBoolean("d6_enabled", d6_enabled);
            b.putBoolean("d8_enabled", d8_enabled);
            b.putBoolean("d10_enabled", d10_enabled);
            b.putBoolean("d12_enabled", d12_enabled);
            b.putBoolean("d20_enabled", d20_enabled);
            b.putBoolean("plus_enabled", plus_enabled);
            b.putBoolean("minus_enabled", minus_enabled);
            b.putBoolean("rollsbar_enabled", rollsbar_enabled);
            b.putString("rollsbar_text", rollsbar_text);
            b.putBoolean("add_enabled", add_enabled);
            b.putBoolean("usesaved_enabled", usesaved_enabled);
            b.putBoolean("clearlast_enabled", clearlast_enabled);
            b.putBoolean("clearall_enabled", clearall_enabled);
            b.putString("mainview_text", mainview_text);
            b.putBoolean("modifier_enabled", modifier_enabled);
            b.putBoolean("save_enabled", save_enabled);
            b.putBoolean("roll_enabled", roll_enabled);
            b.putBoolean("details_enabled", details_enabled);
            b.putIntegerArrayList("d4_rolls", d4_rolls);
            b.putIntegerArrayList("d6_rolls", d6_rolls);
            b.putIntegerArrayList("d8_rolls", d8_rolls);
            b.putIntegerArrayList("d10_rolls", d10_rolls);
            b.putIntegerArrayList("d12_rolls", d12_rolls);
            b.putIntegerArrayList("d20_rolls", d20_rolls);
            b.putStringArrayList("main_text", MainText);
            b.putStringArrayList("Recent Rolls", recentRolls);
            b.putStringArrayList("Recent Roll Results", recentRollResults);
            b.putStringArrayList("Recent Modifiers", recentModifiers);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed(){
        Intent intent = new Intent(Settings.this, MainActivity.class);
        Bundle b = new Bundle();
        b.putString("selected", selected);
        b.putBoolean("d4_enabled", d4_enabled);
        b.putBoolean("d6_enabled", d6_enabled);
        b.putBoolean("d8_enabled", d8_enabled);
        b.putBoolean("d10_enabled", d10_enabled);
        b.putBoolean("d12_enabled", d12_enabled);
        b.putBoolean("d20_enabled", d20_enabled);
        b.putBoolean("plus_enabled", plus_enabled);
        b.putBoolean("minus_enabled", minus_enabled);
        b.putBoolean("rollsbar_enabled", rollsbar_enabled);
        b.putString("rollsbar_text", rollsbar_text);
        b.putBoolean("add_enabled", add_enabled);
        b.putBoolean("usesaved_enabled", usesaved_enabled);
        b.putBoolean("clearlast_enabled", clearlast_enabled);
        b.putBoolean("clearall_enabled", clearall_enabled);
        b.putString("mainview_text", mainview_text);
        b.putBoolean("modifier_enabled", modifier_enabled);
        b.putBoolean("save_enabled", save_enabled);
        b.putBoolean("roll_enabled", roll_enabled);
        b.putBoolean("details_enabled", details_enabled);
        b.putIntegerArrayList("d4_rolls", d4_rolls);
        b.putIntegerArrayList("d6_rolls", d6_rolls);
        b.putIntegerArrayList("d8_rolls", d8_rolls);
        b.putIntegerArrayList("d10_rolls", d10_rolls);
        b.putIntegerArrayList("d12_rolls", d12_rolls);
        b.putIntegerArrayList("d20_rolls", d20_rolls);
        b.putStringArrayList("main_text", MainText);
        b.putStringArrayList("Recent Rolls", recentRolls);
        b.putStringArrayList("Recent Roll Results", recentRollResults);
        b.putStringArrayList("Recent Modifiers", recentModifiers);
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }
}