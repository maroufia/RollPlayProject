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

    private SwitchCompat dark_light; // Declares a private instance variable dark_light of type SwitchCompat, which is a UI component used for toggling between two states (on/off).
    static  final  String PREF_NAME = "MyAppPrefs"; //Declares a constant variable PREF_NAME of type String and assigns it the value "MyAppPrefs". This constant represents the name of the shared preferences file.
    static  final  String DARK_MODE_KEY = "darkMode"; //Declares another constant variable DARK_MODE_KEY of type String and assigns it the value "darkMode".
    SharedPreferences sharedPreferences; //Declares a variable sharedPreferences of type SharedPreferences, which is used to store and retrieve key-value pairs. It will be used to access the shared preferences file.
    ArrayList<String> recentRolls;//Holds the 4 most recent thrown rolls (number of die for each type)
    ArrayList<String> recentRollResults;//Holds the 4 most recent thrown rolls' results
    ArrayList<String> recentModifiers;//Holds the 4 most recent thrown rolls' modifiers

    private String selected; //The currently selected dice
    private ArrayList<String> MainText; //ArrayList that includes the roll's required info for the result calculation (Something like ["+"(sign),"5"(number of dice),"d6"(dice type),"-","14","d10"...])
    private boolean d4_enabled; //True if the d4 dice is enabled
    private boolean d6_enabled; //True if the d6 dice is enabled
    private boolean d8_enabled; //True if the d8 dice is enabled
    private boolean d10_enabled; //True if the d10 dice is enabled
    private boolean d12_enabled; //True if the d12 dice is enabled
    private boolean d20_enabled; //True if the d20 dice is enabled
    private boolean plus_enabled; //True if the plus button is enabled
    private boolean minus_enabled; //True if the minus button is enabled
    private boolean rollsbar_enabled; //True if the switch bar that controls the number of rolls is enabled
    private String rollsbar_text; //Holds the value of the switch bar
    private boolean add_enabled; //True if the add button is enabled
    private boolean usesaved_enabled; //True if the Use Saved Roll button is enabled
    private boolean clearlast_enabled; //True if the Clear Last button is enabled
    private boolean clearall_enabled; //True if the Clear All button is enabled
    private String mainview_text; //Holds the text of the main TextView (where the roll and result are displayed)
    private boolean modifier_enabled; //True if the modifier EditText is enabled
    private boolean save_enabled; //True if the Save Roll button is enabled
    private boolean roll_enabled; //True if the Roll button is enabled
    private boolean details_enabled; //True if the Details button is enabled
    private int rollsbar_value; //Holds the current value of the rollsbar component and is added to the bundle and passed to the "Main Activity" when the back button is pressed
    private String modifier_value; //holds the current value of the modifier component and is added to the bundle and passed to the "Main Activity" when the back button is pressed
    private ArrayList<Integer> d4_rolls; //Holds the values of all the d4 die that were thrown in the previous roll
    private ArrayList<Integer> d6_rolls; //Holds the values of all the d6 die that were thrown in the previous roll
    private ArrayList<Integer> d8_rolls; //Holds the values of all the d8 die that were thrown in the previous roll
    private ArrayList<Integer> d10_rolls; //Holds the values of all the d10 die that were thrown in the previous roll
    private ArrayList<Integer> d12_rolls; //Holds the values of all the d12 die that were thrown in the previous roll
    private ArrayList<Integer> d20_rolls; //Holds the values of all the d20 die that were thrown in the previous roll

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //--------------------------------------------------
        //--------------------------------------------------
        //It saves the variables of the main function in order to return them when the user goes back to the main.
        //--------------------------------------------------
        //--------------------------------------------------

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
        rollsbar_value = bundle.getInt("rollsbar_value");
        modifier_value = bundle.getString("modifier_value");
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
        //It stops the Settings Activity and starts the History Sub activity
        history.setOnClickListener(history_button);

        View.OnClickListener view_delete_lstnr = v -> {
            Intent intent = new Intent(Settings.this, DeleteSavedActivity.class);
            arl.launch(intent);
        };

        view_delete.setOnClickListener(view_delete_lstnr);
        //Checks if darkModeEnabled is true which if it is it checks the switch and it applies dark mode.
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

    //onResume() method override: Overrides the superclass's onResume() method for activity resumption.
    //Default actions: super.onResume() performs default actions during activity resumption.
    //Dark mode handling: Retrieves dark mode preference, sets switch state, and updates app's night mode.
    @Override
    protected void onResume() {
        super.onResume();
        boolean darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        dark_light.setChecked(darkModeEnabled);
        applyDarkMode(darkModeEnabled);
    }
    //Checks if Dark Mode or Light Mode is enabled and sets its theme accordingly.
    void applyDarkMode(boolean darkModeEnabled) {
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //Returns to Main Activity with the button on the top left.
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
            b.putInt("rollsbar_value", rollsbar_value);
            b.putString("modifier_value", modifier_value);
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
            intent.putExtra("Extra", b);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //Returns to Main Activity with the use of the device's back button
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
        b.putInt("rollsbar_value", rollsbar_value);
        b.putString("modifier_value", modifier_value);
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
        intent.putExtra("Extra", b);
        startActivity(intent);
        finish();
    }
}