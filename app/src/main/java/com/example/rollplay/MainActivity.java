package com.example.rollplay;

import static com.example.rollplay.Settings.DARK_MODE_KEY;
import static com.example.rollplay.Settings.PREF_NAME;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

/*
The app's main screen. It includes the main "roll", "save roll", "use saved roll", "roll details" and "settings" buttons.
 */
public class MainActivity extends AppCompatActivity {

    private String selected; //The currently selected dice
    private String new_selected; //The newly selected dice
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
    private ArrayList<Integer> d4_rolls; //Holds the values of all the d4 die that were thrown in the previous roll
    private ArrayList<Integer> d6_rolls; //Holds the values of all the d6 die that were thrown in the previous roll
    private ArrayList<Integer> d8_rolls; //Holds the values of all the d8 die that were thrown in the previous roll
    private ArrayList<Integer> d10_rolls; //Holds the values of all the d10 die that were thrown in the previous roll
    private ArrayList<Integer> d12_rolls; //Holds the values of all the d12 die that were thrown in the previous roll
    private ArrayList<Integer> d20_rolls; //Holds the values of all the d20 die that were thrown in the previous roll
    private ArrayList<String> recentRolls; //Holds the 4 most recent thrown rolls (number of die for each type)
    private ArrayList<String> recentRollResults; //Holds the 4 most recent thrown rolls' results
    private ArrayList<String> recentModifiers; //Holds the 4 most recent thrown rolls' modifiers
    SharedPreferences sharedPreferences; //Declares a variable sharedPreferences of type SharedPreferences, which is used to store and retrieve key-value pairs. It will be used to access the shared preferences file.

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();

        //Checks if Dark Mode or Light Mode is enabled and sets its theme accordingly.
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);

        //--------------------------------------------------
        //--------------------------------------------------
        //Initializes the activity's variables
        //If the activity happens to be recreated the values are retrieved from the savedInstanceState bundle
        //--------------------------------------------------
        //--------------------------------------------------

        if (savedInstanceState != null) { //The activity was recreated
            selected = savedInstanceState.getString("selected");
            new_selected = "none";
            d4_enabled = savedInstanceState.getBoolean("d4_enabled");
            d6_enabled = savedInstanceState.getBoolean("d6_enabled");
            d8_enabled = savedInstanceState.getBoolean("d8_enabled");
            d10_enabled = savedInstanceState.getBoolean("d10_enabled");
            d12_enabled = savedInstanceState.getBoolean("d12_enabled");
            d20_enabled = savedInstanceState.getBoolean("d20_enabled");
            plus_enabled = savedInstanceState.getBoolean("plus_enabled");
            minus_enabled = savedInstanceState.getBoolean("minus_enabled");
            rollsbar_enabled = savedInstanceState.getBoolean("rollsbar_enabled");
            rollsbar_text = savedInstanceState.getString("rollsbar_text");
            add_enabled = savedInstanceState.getBoolean("add_enabled");
            usesaved_enabled = savedInstanceState.getBoolean("usesaved_enabled");
            clearlast_enabled = savedInstanceState.getBoolean("clearlast_enabled");
            clearall_enabled = savedInstanceState.getBoolean("clearall_enabled");
            mainview_text = savedInstanceState.getString("mainview_text");
            modifier_enabled = savedInstanceState.getBoolean("modifier_enabled");
            save_enabled = savedInstanceState.getBoolean("save_enabled");
            roll_enabled = savedInstanceState.getBoolean("roll_enabled");
            details_enabled = savedInstanceState.getBoolean("details_enabled");
            d4_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d4_rolls"));
            d6_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d6_rolls"));
            d8_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d8_rolls"));
            d10_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d10_rolls"));
            d12_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d12_rolls"));
            d20_rolls = new ArrayList<>(savedInstanceState.getIntegerArrayList("d20_rolls"));
            recentRolls = new ArrayList<>(savedInstanceState.getStringArrayList("recentRolls"));
            recentRollResults = new ArrayList<>(savedInstanceState.getStringArrayList("recentRollResults"));
            recentModifiers = new ArrayList<>(savedInstanceState.getStringArrayList("recentModifiers"));
            MainText = new ArrayList<>(savedInstanceState.getStringArrayList("main_text"));
        }
        else { //The activity is created for the first time
            selected = "none";
            new_selected = "none";
            d4_enabled = true;
            d6_enabled = true;
            d8_enabled = true;
            d10_enabled = true;
            d12_enabled = true;
            d20_enabled = true;
            plus_enabled = false;
            minus_enabled = false;
            rollsbar_enabled = false;
            rollsbar_text = "0";
            add_enabled = false;
            usesaved_enabled = true;
            clearlast_enabled = false;
            clearall_enabled = false;
            mainview_text = "";
            modifier_enabled = true;
            save_enabled = false;
            roll_enabled = false;
            details_enabled = false;
            d4_rolls = new ArrayList<>();
            d6_rolls = new ArrayList<>();
            d8_rolls = new ArrayList<>();
            d10_rolls = new ArrayList<>();
            d12_rolls = new ArrayList<>();
            d20_rolls = new ArrayList<>();
            recentRolls = new ArrayList<>(4);
            recentRollResults = new ArrayList<>(4);
            recentModifiers = new ArrayList<>(4);
            MainText = new ArrayList<>();
        }

        //--------------------------------------------------
        //--------------------------------------------------
        //After coming back from the Settings Activity (Main Activity gets restarted):
        //--------------------------------------------------
        //--------------------------------------------------

        int rollbar_value = 0; //SeekBar's progress
        String modifier_value = ""; //EditText modifier's value

        Bundle settings_bundle = getIntent().getBundleExtra("Extra"); //Intent from Settings
        if (settings_bundle != null) {
            selected = settings_bundle.getString("selected");
            d4_enabled = settings_bundle.getBoolean("d4_enabled");
            d6_enabled = settings_bundle.getBoolean("d6_enabled");
            d8_enabled = settings_bundle.getBoolean("d8_enabled");
            d10_enabled = settings_bundle.getBoolean("d10_enabled");
            d12_enabled = settings_bundle.getBoolean("d12_enabled");
            d20_enabled = settings_bundle.getBoolean("d20_enabled");
            plus_enabled = settings_bundle.getBoolean("plus_enabled");
            minus_enabled = settings_bundle.getBoolean("minus_enabled");
            rollsbar_enabled = settings_bundle.getBoolean("rollsbar_enabled");
            rollsbar_text = settings_bundle.getString("rollsbar_text");
            add_enabled = settings_bundle.getBoolean("add_enabled");
            usesaved_enabled = settings_bundle.getBoolean("usesaved_enabled");
            clearlast_enabled = settings_bundle.getBoolean("clearlast_enabled");
            clearall_enabled = settings_bundle.getBoolean("clearall_enabled");
            mainview_text = settings_bundle.getString("mainview_text");
            modifier_enabled = settings_bundle.getBoolean("modifier_enabled");
            save_enabled = settings_bundle.getBoolean("save_enabled");
            roll_enabled = settings_bundle.getBoolean("roll_enabled");
            details_enabled = settings_bundle.getBoolean("details_enabled");
            rollbar_value = settings_bundle.getInt("rollsbar_value");
            modifier_value = settings_bundle.getString("modifier_value");
            d4_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d4_rolls"));
            d6_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d6_rolls"));
            d8_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d8_rolls"));
            d10_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d10_rolls"));
            d12_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d12_rolls"));
            d20_rolls = new ArrayList<>(settings_bundle.getIntegerArrayList("d20_rolls"));
            MainText = new ArrayList<>(settings_bundle.getStringArrayList("main_text"));
            recentRolls = new ArrayList<>(4);
            recentRollResults = new ArrayList<>(4);
            recentModifiers = new ArrayList<>(4);
            recentRolls.addAll(settings_bundle.getStringArrayList("Recent Rolls"));
            recentRollResults.addAll(settings_bundle.getStringArrayList("Recent Roll Results"));
            recentModifiers.addAll(settings_bundle.getStringArrayList("Recent Modifiers"));
            getIntent().removeExtra("Extra"); //Removes the settings_bundle after being used as to prevent multiple uses due to screen rotations etc.
        }

        //d4 dice initialization
        ImageButton d4 = findViewById(R.id.d4_button);
        d4.setEnabled(d4_enabled);
        if (!d4_enabled)
            d4.setImageResource(R.drawable.d4_disabled);

        //d6 dice initialization
        ImageButton d6 = findViewById(R.id.d6_button);
        d6.setEnabled(d6_enabled);
        if (!d6_enabled)
            d6.setImageResource(R.drawable.d6_disabled);

        //d8 dice initialization
        ImageButton d8 = findViewById(R.id.d8_button);
        d8.setEnabled(d8_enabled);
        if (!d8_enabled)
            d8.setImageResource(R.drawable.d8_disabled);

        //d10 dice initialization
        ImageButton d10 = findViewById(R.id.d10_button);
        d10.setEnabled(d10_enabled);
        if (!d10_enabled)
            d10.setImageResource(R.drawable.d10_disabled);

        //d12 dice initialization
        ImageButton d12 = findViewById(R.id.d12_button);
        d12.setEnabled(d12_enabled);
        if (!d12_enabled)
            d12.setImageResource(R.drawable.d12_disabled);

        //d20 dice initialization
        ImageButton d20 = findViewById(R.id.d20_button);
        d20.setEnabled(d20_enabled);
        if (!d20_enabled)
            d20.setImageResource(R.drawable.d20_disabled);

        //Checks if all die should be disabled
        if (plus_enabled || details_enabled) {
            d4.setEnabled(false);
            d4.setImageResource(R.drawable.d4_disabled);
            d6.setEnabled(false);
            d6.setImageResource(R.drawable.d6_disabled);
            d8.setEnabled(false);
            d8.setImageResource(R.drawable.d8_disabled);
            d10.setEnabled(false);
            d10.setImageResource(R.drawable.d10_disabled);
            d12.setEnabled(false);
            d12.setImageResource(R.drawable.d12_disabled);
            d20.setEnabled(false);
            d20.setImageResource(R.drawable.d20_disabled);
        }

        //Checks if a dice is selected
        switch (selected) {
            case "d4":
                d4.setImageResource(R.drawable.d4_selected);
                break;
            case "d6":
                d6.setImageResource(R.drawable.d6_selected);
                break;
            case "d8":
                d8.setImageResource(R.drawable.d8_selected);
                break;
            case "d10":
                d10.setImageResource(R.drawable.d10_selected);
                break;
            case "d12":
                d12.setImageResource(R.drawable.d12_selected);
                break;
            case "d20":
                d20.setImageResource(R.drawable.d20_selected);
                break;
            case "none":
                break;
        }

        //Plus button initialization
        ImageButton plus_button = findViewById(R.id.plus_button);
        plus_button.setEnabled(plus_enabled);
        if (!plus_enabled)
            plus_button.setImageResource(R.drawable.plus_disabled);

        //Minus button initialization
        ImageButton minus_button = findViewById(R.id.minus_button);
        minus_button.setEnabled(minus_enabled);
        if (!minus_enabled)
            minus_button.setImageResource(R.drawable.minus_disabled);

        //Seekbar initialization
        SeekBar rolls_bar = findViewById(R.id.rolls_bar);
        rolls_bar.setEnabled(rollsbar_enabled);
        rolls_bar.setProgress(rollbar_value);
        TextView bar_display = findViewById(R.id.bar_display);
        bar_display.setText(rollsbar_text);
        Button add_button = findViewById(R.id.add_roll_button);
        add_button.setEnabled(add_enabled);

        //Use Saved button initialization
        Button use_saved = findViewById(R.id.use_saved_button);
        use_saved.setEnabled(usesaved_enabled);

        //Clear Last button initialization
        Button clear_last = findViewById(R.id.clear_last_button);
        clear_last.setEnabled(clearlast_enabled);

        //Clear All button initialization
        Button clear_all = findViewById(R.id.clear_all_button);
        clear_all.setEnabled(clearall_enabled);

        //Main TextView initialization
        TextView main_view = findViewById(R.id.main_view);
        main_view.setText(mainview_text);

        //Modifier EditText initialization
        EditText modifier_input = findViewById(R.id.modifier_input);
        modifier_input.setText(modifier_value);
        modifier_input.setEnabled(modifier_enabled);

        //Save Roll button initialization
        Button save_roll = findViewById(R.id.save_roll_button);
        save_roll.setEnabled(save_enabled);

        //Roll button initialization
        Button roll_button = findViewById(R.id.roll_button);
        roll_button.setEnabled(roll_enabled);

        //Details button initialization
        Button details = findViewById(R.id.details_button);
        details.setEnabled(details_enabled);

        //Settings button initialization
        ImageButton settings = findViewById(R.id.settings_button);
        settings.setEnabled(true);

        //Called for launching sub-activities and getting back their result ("Save Roll", "Use Saved Roll", "Details")
        ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
                result -> {

                    /*
                    "Use Saved Roll" sub-activity ends
                    All necessary variables get updated in order to reflect the selected roll
                     */
                    if (result.getResultCode() == 2) {

                        Intent data = result.getData();
                        assert data != null;
                        Bundle b = data.getExtras();

                        if (b != null) {
                            String[] MainTextTemp = b.getStringArray("Main Text");
                            MainText.clear();
                            Collections.addAll(MainText, MainTextTemp);
                            modifier_input.setText(b.getString("Modifier"));
                            int[] selectedDice = b.getIntArray("Dice");
                            mainview_text = b.getString("Display");
                            main_view.setText(mainview_text);
                            for (int i = 0; i < selectedDice.length; i++) {
                                switch (i) {
                                    case 0:
                                        if (selectedDice[i] == 0)
                                            d4_enabled = false;
                                        break;
                                    case 1:
                                        if (selectedDice[i] == 0)
                                            d6_enabled = false;
                                        break;
                                    case 2:
                                        if (selectedDice[i] == 0)
                                            d8_enabled = false;
                                        break;
                                    case 3:
                                        if (selectedDice[i] == 0)
                                            d10_enabled = false;
                                        break;
                                    case 4:
                                        if (selectedDice[i] == 0)
                                            d12_enabled = false;
                                        break;
                                    case 5:
                                        if (selectedDice[i] == 0)
                                            d20_enabled = false;
                                        break;
                                }
                            }
                            d4.setEnabled(false);
                            d4.setImageResource(R.drawable.d4_disabled);
                            d6.setEnabled(false);
                            d6.setImageResource(R.drawable.d6_disabled);
                            d8.setEnabled(false);
                            d8.setImageResource(R.drawable.d8_disabled);
                            d10.setEnabled(false);
                            d10.setImageResource(R.drawable.d10_disabled);
                            d12.setEnabled(false);
                            d12.setImageResource(R.drawable.d12_disabled);
                            d20.setEnabled(false);
                            d20.setImageResource(R.drawable.d20_disabled);
                            plus_button.setEnabled(true);
                            plus_enabled = true;
                            plus_button.setImageResource(R.drawable.plus);
                            minus_button.setImageResource(R.drawable.minus);
                            minus_enabled = true;
                            minus_button.setEnabled(true);
                            clear_last.setEnabled(true);
                            clearlast_enabled = true;
                            clear_all.setEnabled(true);
                            clearall_enabled = true;
                            save_roll.setEnabled(true);
                            save_enabled = true;
                            roll_button.setEnabled(true);
                            roll_enabled = true;

                        }
                    }

                    /*
                    "Save Roll" sub-activity ends
                    Deletes the 2 last values of the MainText array (modifier info) as it is no longer of use
                     */
                    else if (result.getResultCode() == 3) {
                        if (MainText.contains("mod")) {
                            MainText.remove(MainText.size() - 1);
                            MainText.remove(MainText.size() - 1);
                        }
                    }
                });

        //--------------------------------------------------
        //--------------------------------------------------
        //Listeners:
        //--------------------------------------------------
        //--------------------------------------------------

        /*
        Listener for all the different dice types
        Changes the selected dice and all the necessary dice images
        If the user presses an already selected dice it gets unselected
         */
        View.OnClickListener DiceButtons = v -> {
            int id = v.getId();
            if (id == R.id.d4_button) {
                new_selected = "d4";
                d4.setImageResource(R.drawable.d4_selected);
            }
            else if (id == R.id.d6_button) {
                new_selected = "d6";
                d6.setImageResource(R.drawable.d6_selected);
            }
            else if (id == R.id.d8_button) {
                new_selected = "d8";
                d8.setImageResource(R.drawable.d8_selected);
            }
            else if (id == R.id.d10_button) {
                new_selected = "d10";
                d10.setImageResource(R.drawable.d10_selected);
            }
            else if (id == R.id.d12_button) {
                new_selected = "d12";
                d12.setImageResource(R.drawable.d12_selected);
            }
            else if (id == R.id.d20_button) {
                new_selected = "d20";
                d20.setImageResource(R.drawable.d20_selected);
            }
            if (!selected.equals(new_selected)) { //If the user changed his selection
                switch(selected) {
                    case "d4":
                        d4.setImageResource(R.drawable.d4);
                        break;
                    case "d6":
                        d6.setImageResource(R.drawable.d6);
                        break;
                    case "d8":
                        d8.setImageResource(R.drawable.d8);
                        break;
                    case "d10":
                        d10.setImageResource(R.drawable.d10);
                        break;
                    case "d12":
                        d12.setImageResource(R.drawable.d12);
                        break;
                    case "d20":
                        d20.setImageResource(R.drawable.d20);
                        break;
                    case "none":
                        break;
                }
                selected = new_selected;
                rolls_bar.setEnabled(true);
                rollsbar_enabled = true;
                rolls_bar.setProgress(1);
                rollsbar_text = "1";
                bar_display.setText("1");
                add_button.setEnabled(true);
                add_enabled = true;
            }
            else { //If the user unselected the previously selected dice
                switch(new_selected) {
                    case "d4":
                        d4.setImageResource(R.drawable.d4);
                        break;
                    case "d6":
                        d6.setImageResource(R.drawable.d6);
                        break;
                    case "d8":
                        d8.setImageResource(R.drawable.d8);
                        break;
                    case "d10":
                        d10.setImageResource(R.drawable.d10);
                        break;
                    case "d12":
                        d12.setImageResource(R.drawable.d12);
                        break;
                    case "d20":
                        d20.setImageResource(R.drawable.d20);
                        break;
                }
                selected = "none";
                rolls_bar.setEnabled(false);
                rollsbar_enabled = false;
                bar_display.setText("0");
                rollsbar_text = "0";
                add_button.setEnabled(false);
                add_enabled = false;
            }
        };

        d4.setOnClickListener(DiceButtons);
        d6.setOnClickListener(DiceButtons);
        d8.setOnClickListener(DiceButtons);
        d10.setOnClickListener(DiceButtons);
        d12.setOnClickListener(DiceButtons);
        d20.setOnClickListener(DiceButtons);

        /*
        Listener for the SeekBar that changes the number of die for the roll
        With every change, the appropriate variables are updated
         */
        rolls_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bar_display.setText(String.valueOf(seekBar.getProgress()));
                rollsbar_text = String.valueOf(seekBar.getProgress());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        /*
        Listener for the plus and minus buttons
        Updates the necessary variables
        After one of them is selected, the buttons get disabled and other views are enabled
         */
        View.OnClickListener Plus_Minus_Buttons = v -> {
            int id = v.getId();
            if (id == R.id.plus_button) {
                MainText.add("+");
                mainview_text += " + ";
                main_view.append(" + ");
            } else if (id == R.id.minus_button) {
                MainText.add("-");
                mainview_text += " - ";
                main_view.append(" - ");
            }
            plus_button.setEnabled(false);
            plus_enabled = false;
            plus_button.setImageResource(R.drawable.plus_disabled);
            minus_button.setEnabled(false);
            minus_enabled = false;
            minus_button.setImageResource(R.drawable.minus_disabled);
            if (d4_enabled) {
                d4.setEnabled(true);
                d4.setImageResource(R.drawable.d4);
            }
            if (d6_enabled) {
                d6.setEnabled(true);
                d6.setImageResource(R.drawable.d6);
            }
            if (d8_enabled) {
                d8.setEnabled(true);
                d8.setImageResource(R.drawable.d8);
            }
            if (d10_enabled) {
                d10.setEnabled(true);
                d10.setImageResource(R.drawable.d10);
            }
            if (d12_enabled) {
                d12.setEnabled(true);
                d12.setImageResource(R.drawable.d12);
            }
            if (d20_enabled) {
                d20.setEnabled(true);
                d20.setImageResource(R.drawable.d20);
            }
            save_roll.setEnabled(false);
            save_enabled = false;
            roll_button.setEnabled(false);
            roll_enabled = false;
        };

        plus_button.setOnClickListener(Plus_Minus_Buttons);
        minus_button.setOnClickListener(Plus_Minus_Buttons);

        /*
        Listener for the Add button
        Adds the SeekBar amount of selected dice to the current roll by updating all necessary variables
        Die (selected dice gets permanently disabled) and SeekBar are disabled and other views are enabled
         */
        View.OnClickListener add_button_listener = v -> {
            MainText.add(bar_display.getText().toString());
            MainText.add(selected);
            mainview_text += rolls_bar.getProgress() + selected;
            main_view.append(rolls_bar.getProgress() + selected);
            switch (selected) {
                case "d4":
                    d4_enabled = false;
                    break;
                case "d6":
                    d6_enabled = false;
                    break;
                case "d8":
                    d8_enabled = false;
                    break;
                case "d10":
                    d10_enabled = false;
                    break;
                case "d12":
                    d12_enabled = false;
                    break;
                case "d20":
                    d20_enabled = false;
                    break;
            }
            d4.setEnabled(false);
            d4.setImageResource(R.drawable.d4_disabled);
            d6.setEnabled(false);
            d6.setImageResource(R.drawable.d6_disabled);
            d8.setEnabled(false);
            d8.setImageResource(R.drawable.d8_disabled);
            d10.setEnabled(false);
            d10.setImageResource(R.drawable.d10_disabled);
            d12.setEnabled(false);
            d12.setImageResource(R.drawable.d12_disabled);
            d20.setEnabled(false);
            d20.setImageResource(R.drawable.d20_disabled);
            selected = "none";
            add_button.setEnabled(false);
            add_enabled = false;
            rolls_bar.setEnabled(false);
            rollsbar_enabled = false;
            rolls_bar.setProgress(1);
            bar_display.setText("0");
            rollsbar_text = "0";
            plus_button.setEnabled(true);
            plus_enabled = true;
            plus_button.setImageResource(R.drawable.plus);
            minus_button.setEnabled(true);
            minus_enabled = true;
            minus_button.setImageResource(R.drawable.minus);
            clear_last.setEnabled(true);
            clearlast_enabled = true;
            clear_all.setEnabled(true);
            clearall_enabled = true;
            save_roll.setEnabled(true);
            save_enabled = true;
            roll_button.setEnabled(true);
            roll_enabled = true;
        };

        add_button.setOnClickListener(add_button_listener);

        /*
        Listener for the 2 Clear buttons
        If Clear Last is pressed the last addition to the current roll gets deleted (either dice or operator)
        If Clear All is pressed everything gets deleted
         */
        View.OnClickListener ClearSelectionsButtons = v -> {
            int id = v.getId();
            if (id == R.id.clear_last_button) { //Last addition gets deleted, views are updated accordingly
                String LastInput = MainText.get(MainText.size()-1);
                if ("+".equals(LastInput) || "-".equals(LastInput)) {
                    MainText.remove(MainText.size() - 1);
                    d4.setEnabled(false);
                    d4.setImageResource(R.drawable.d4_disabled);
                    d6.setEnabled(false);
                    d6.setImageResource(R.drawable.d6_disabled);
                    d8.setEnabled(false);
                    d8.setImageResource(R.drawable.d8_disabled);
                    d10.setEnabled(false);
                    d10.setImageResource(R.drawable.d10_disabled);
                    d12.setEnabled(false);
                    d12.setImageResource(R.drawable.d12_disabled);
                    d20.setEnabled(false);
                    d20.setImageResource(R.drawable.d20_disabled);
                    plus_button.setEnabled(true);
                    plus_enabled = true;
                    plus_button.setImageResource(R.drawable.plus);
                    minus_button.setEnabled(true);
                    minus_enabled = true;
                    minus_button.setImageResource(R.drawable.minus);
                    save_roll.setEnabled(true);
                    save_enabled = true;
                    roll_button.setEnabled(true);
                    roll_enabled = true;
                }
                else {
                    String deletedSelection = MainText.get(MainText.size() - 1);
                    MainText.remove(MainText.size()-1);
                    MainText.remove(MainText.size()-1);
                    switch (deletedSelection) {
                        case "d4":
                            d4_enabled = true;
                            break;
                        case "d6":
                            d6_enabled = true;
                            break;
                        case "d8":
                            d8_enabled = true;
                            break;
                        case "d10":
                            d10_enabled = true;
                            break;
                        case "d12":
                            d12_enabled = true;
                            break;
                        case "d20":
                            d20_enabled = true;
                            break;
                    }
                    if (d4_enabled) {
                        d4.setEnabled(true);
                        d4.setImageResource(R.drawable.d4);
                    }
                    if (d6_enabled) {
                        d6.setEnabled(true);
                        d6.setImageResource(R.drawable.d6);
                    }
                    if (d8_enabled) {
                        d8.setEnabled(true);
                        d8.setImageResource(R.drawable.d8);
                    }
                    if (d10_enabled) {
                        d10.setEnabled(true);
                        d10.setImageResource(R.drawable.d10);
                    }
                    if (d12_enabled) {
                        d12.setEnabled(true);
                        d12.setImageResource(R.drawable.d12);
                    }
                    if (d20_enabled) {
                        d20.setEnabled(true);
                        d20.setImageResource(R.drawable.d20);
                    }
                    plus_button.setEnabled(false);
                    plus_enabled = false;
                    plus_button.setImageResource(R.drawable.plus_disabled);
                    minus_button.setEnabled(false);
                    minus_enabled = false;
                    minus_button.setImageResource(R.drawable.minus_disabled);
                    save_roll.setEnabled(false);
                    save_enabled = false;
                    roll_button.setEnabled(false);
                    roll_enabled = false;
                }
                mainview_text = "";
                main_view.setText("");
                String text;
                if (MainText.size() > 0) { //If there's still roll data, the roll gets reconstructed without the deleted data
                    for (int i = 0; i < MainText.size(); i++) {
                        text = MainText.get(i);
                        if (text.equals("+") || text.equals("-"))
                            main_view.append(" " + text + " ");
                        else
                            main_view.append(text);
                    }
                    mainview_text = main_view.getText().toString();
                }
                else {
                    clear_last.setEnabled(false);
                    clearlast_enabled = false;
                    clear_all.setEnabled(false);
                    clearall_enabled = false;
                }
            }
            else { //The whole roll gets deleted (essentially a full reset)
                MainText.clear();
                mainview_text = "";
                main_view.setText("");
                d4_enabled = true;
                d6_enabled = true;
                d8_enabled = true;
                d10_enabled = true;
                d12_enabled = true;
                d20_enabled = true;
                d4.setEnabled(true);
                d4.setImageResource(R.drawable.d4);
                d6.setEnabled(true);
                d6.setImageResource(R.drawable.d6);
                d8.setEnabled(true);
                d8.setImageResource(R.drawable.d8);
                d10.setEnabled(true);
                d10.setImageResource(R.drawable.d10);
                d12.setEnabled(true);
                d12.setImageResource(R.drawable.d12);
                d20.setEnabled(true);
                d20.setImageResource(R.drawable.d20);
                plus_button.setEnabled(false);
                plus_enabled = false;
                plus_button.setImageResource(R.drawable.plus_disabled);
                minus_button.setEnabled(false);
                minus_enabled = false;
                minus_button.setImageResource(R.drawable.minus_disabled);
                clear_last.setEnabled(false);
                clearlast_enabled = false;
                clear_all.setEnabled(false);
                clearall_enabled = false;
                details.setEnabled(false);
                details_enabled = false;
                save_roll.setEnabled(false);
                save_enabled = false;
                modifier_input.setText("");
                modifier_input.setEnabled(true);
                modifier_enabled = true;
                roll_button.setEnabled(false);
                roll_enabled = false;
            }
            selected = "none";
            use_saved.setEnabled(true);
            usesaved_enabled = true;
            rolls_bar.setEnabled(false);
            rollsbar_enabled = false;
            rolls_bar.setProgress(0);
            bar_display.setText("0");
            rollsbar_text = "0";
            add_button.setEnabled(false);
            add_enabled = false;
        };

        clear_last.setOnClickListener(ClearSelectionsButtons);
        clear_all.setOnClickListener(ClearSelectionsButtons);

        /*
        Listener for the Roll button
        Using the MainText arraylist, calculates and updates the main TextView with the roll's result
        Updates the remaining arraylists with data used in the Details sub-activity and Settings activity
        All appropriate views are updated
         */
        View.OnClickListener RollButton = v -> {
            d4_rolls = new ArrayList<>();
            d6_rolls = new ArrayList<>();
            d8_rolls = new ArrayList<>();
            d10_rolls = new ArrayList<>();
            d12_rolls = new ArrayList<>();
            d20_rolls = new ArrayList<>();
            int result = 0;
            int i = 0;
            String operation = "addition";
            if (MainText.get(0).equals("-")) {
                operation = "subtraction";
                i++;
            }
            while (i+1 < MainText.size()) {
                String character = MainText.get(i+1); //Character could be "d4", "d6", "d8", "d10", "d12", "d20"
                int dice_sum = 0;
                int random_value;
                Random r = new Random();
                switch (character) { //For every character roll the corresponding dice the selected amount of time
                    case "d4":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(4);
                            dice_sum += random_value;
                            d4_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "d6":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(6);
                            dice_sum += random_value;
                            d6_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "d8":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(8);
                            dice_sum += random_value;
                            d8_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "d10":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(10);
                            dice_sum += random_value;
                            d10_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "d12":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(12);
                            dice_sum += random_value;
                            d12_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "d20":
                        for (int j = 0; j < Integer.parseInt(MainText.get(i)); j++) {
                            random_value = 1 + r.nextInt(20);
                            dice_sum += random_value;
                            d20_rolls.add(random_value);
                        }
                        i++;
                        break;
                    case "+":
                        operation = "addition";
                        i+=2;
                        break;
                    case "-":
                        operation = "subtraction";
                        i+=2;
                        break;
                }
                switch (operation) {
                    case "addition":
                        result += dice_sum;
                        break;
                    case "subtraction":
                        result -= dice_sum;
                        break;
                }
            }
            if (recentModifiers.size() == 4)
                recentModifiers.remove(0);
            if (!modifier_input.getText().toString().equals("") && !modifier_input.getText().toString().equals("0") && !modifier_input.getText().toString().equals("-0")) {
                result += Integer.parseInt(modifier_input.getText().toString());

                if (Integer.parseInt(modifier_input.getText().toString()) > 0)
                    recentModifiers.add(" + " + modifier_input.getText().toString());

                else {
                    recentModifiers.add(" - " + Integer.parseInt(modifier_input.getText().toString()) * (-1));
                }
            }
            else
                recentModifiers.add("0");

            if (recentRolls.size() == 4)
                recentRolls.remove(0);
            recentRolls.add(main_view.getText().toString());

            if (recentRollResults.size() == 4)
                recentRollResults.remove(0);
            recentRollResults.add(Integer.toString(result));

            String string = "Result:\n" + result;
            mainview_text = string;
            main_view.setText(string);
            plus_button.setEnabled(false);
            plus_enabled = false;
            plus_button.setImageResource(R.drawable.plus_disabled);
            minus_button.setEnabled(false);
            minus_enabled = false;
            minus_button.setImageResource(R.drawable.minus_disabled);
            clear_last.setEnabled(false);
            clearlast_enabled = false;
            details.setEnabled(true);
            details_enabled = true;
            use_saved.setEnabled(false);
            usesaved_enabled = false;
            modifier_input.setEnabled(false);
            modifier_enabled = false;
            roll_button.setEnabled(false);
            roll_enabled = false;
        };
        roll_button.setOnClickListener(RollButton);

        /*
        Listener for the Save Roll button
        Starts the SaveActivity sub-activity
        Passes the MainText arraylist along with 2 more cells of information for the modifier
         */
        View.OnClickListener SaveButton = v -> {
            if (!modifier_input.getText().toString().equals("")) {
                MainText.add("mod");
                MainText.add(modifier_input.getText().toString());
            }
            Intent intent = new Intent(MainActivity.this, SaveActivity.class);
            Bundle bundle  = new Bundle();
            bundle.putStringArrayList("Roll", MainText);
            intent.putExtras(bundle);
            arl.launch(intent);
        };

        save_roll.setOnClickListener(SaveButton);

        /*
        Listener for the Use Saved Roll button
        Starts the UseSavedActivity sub-activity
         */
        View.OnClickListener useSavedButton = v -> {
            Intent intent = new Intent(MainActivity.this, UseSavedActivity.class);
            arl.launch(intent);
        };

        use_saved.setOnClickListener(useSavedButton);

        /*
        Listener for the Details Button
        Starts the DetailsActivity sub-activity
        Passes the _rolls arraylists
         */
        View.OnClickListener detailsButton = v -> {
            Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
            Bundle bundle  = new Bundle();
            bundle.putIntegerArrayList("d4", d4_rolls);
            bundle.putIntegerArrayList("d6", d6_rolls);
            bundle.putIntegerArrayList("d8", d8_rolls);
            bundle.putIntegerArrayList("d10", d10_rolls);
            bundle.putIntegerArrayList("d12", d12_rolls);
            bundle.putIntegerArrayList("d20", d20_rolls);

            intent.putExtras(bundle);

            arl.launch(intent);

        };

        details.setOnClickListener(detailsButton);

        /*
        Listener for the Settings button
        Starts the SettingsActivity activity
        Passes all variables necessary to restore the MainActivity to its current state after killing it
         */
        View.OnClickListener settingsButton = v -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
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
            b.putInt("rollsbar_value", rolls_bar.getProgress());
            b.putString("modifier_value", modifier_input.getText().toString());
            b.putStringArrayList("Recent Rolls", recentRolls);
            b.putStringArrayList("Recent Roll Results", recentRollResults);
            b.putStringArrayList("Recent Modifiers", recentModifiers);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        };

        settings.setOnClickListener(settingsButton);
    }

    //Remembers if dark Mode is enabled when the App opens (or is resumed)
    @Override
    protected void onResume() {
        super.onResume();
        boolean darkModeEnabled = sharedPreferences.getBoolean(DARK_MODE_KEY, false);
        applyDarkMode(darkModeEnabled);
    }

    //Determines if dark mode is enabled or not.
    void applyDarkMode(boolean darkModeEnabled) {
        if (darkModeEnabled) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    //Saves the activity's current state by bundling all necessary variables before recreating it
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("selected", selected);
        outState.putBoolean("d4_enabled", d4_enabled);
        outState.putBoolean("d6_enabled", d6_enabled);
        outState.putBoolean("d8_enabled", d8_enabled);
        outState.putBoolean("d10_enabled", d10_enabled);
        outState.putBoolean("d12_enabled", d12_enabled);
        outState.putBoolean("d20_enabled", d20_enabled);
        outState.putBoolean("plus_enabled", plus_enabled);
        outState.putBoolean("minus_enabled", minus_enabled);
        outState.putBoolean("rollsbar_enabled", rollsbar_enabled);
        outState.putString("rollsbar_text", rollsbar_text);
        outState.putBoolean("add_enabled", add_enabled);
        outState.putBoolean("usesaved_enabled", usesaved_enabled);
        outState.putBoolean("clearlast_enabled", clearlast_enabled);
        outState.putBoolean("clearall_enabled", clearall_enabled);
        outState.putString("mainview_text", mainview_text);
        outState.putBoolean("modifier_enabled", modifier_enabled);
        outState.putBoolean("save_enabled", save_enabled);
        outState.putBoolean("roll_enabled", roll_enabled);
        outState.putBoolean("details_enabled", details_enabled);
        outState.putIntegerArrayList("d4_rolls", d4_rolls);
        outState.putIntegerArrayList("d6_rolls", d6_rolls);
        outState.putIntegerArrayList("d8_rolls", d8_rolls);
        outState.putIntegerArrayList("d10_rolls", d10_rolls);
        outState.putIntegerArrayList("d12_rolls", d12_rolls);
        outState.putIntegerArrayList("d20_rolls", d20_rolls);
        outState.putStringArrayList("recentRolls", recentRolls);
        outState.putStringArrayList("recentRollResults", recentRollResults);
        outState.putStringArrayList("recentModifiers", recentModifiers);
        outState.putStringArrayList("main_text", MainText);
        super.onSaveInstanceState(outState);
    }
}