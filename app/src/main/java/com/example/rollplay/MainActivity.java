package com.example.rollplay;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String selected = "none";
    private String new_selected = "none";
    private boolean d4_enabled = true;
    private boolean d6_enabled = true;
    private boolean d8_enabled = true;
    private boolean d10_enabled = true;
    private boolean d12_enabled = true;
    private boolean d20_enabled = true;
    private final ArrayList<String> recentRolls = new ArrayList<>(4);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton d4 = findViewById(R.id.d4_button);
        ImageButton d6 = findViewById(R.id.d6_button);
        ImageButton d8 = findViewById(R.id.d8_button);
        ImageButton d10 = findViewById(R.id.d10_button);
        ImageButton d12 = findViewById(R.id.d12_button);
        ImageButton d20 = findViewById(R.id.d20_button);

        ImageButton plus_button = findViewById(R.id.plus_button);
        plus_button.setEnabled(false);
        plus_button.setImageResource(R.drawable.plus_disabled);
        ImageButton minus_button = findViewById(R.id.minus_button);
        minus_button.setImageResource(R.drawable.minus_disabled);
        minus_button.setEnabled(false);

        SeekBar rolls_bar = findViewById(R.id.rolls_bar);
        rolls_bar.setEnabled(false);
        TextView bar_display = findViewById(R.id.bar_display);
        bar_display.setText("0");
        Button add_button = findViewById(R.id.add_roll_button);
        add_button.setEnabled(false);

        Button use_saved = findViewById(R.id.use_saved_button);
        use_saved.setEnabled(true);
        Button clear_last = findViewById(R.id.clear_last_button);
        clear_last.setEnabled(false);
        Button clear_all = findViewById(R.id.clear_all_button);
        clear_all.setEnabled(false);

        TextView main_view = findViewById(R.id.main_view);
        main_view.setText("");

        EditText modifier_input = findViewById(R.id.modifier_input);
        modifier_input.setEnabled(true);

        Button save_roll = findViewById(R.id.save_roll_button);
        save_roll.setEnabled(false);
        Button roll_value = findViewById(R.id.roll_button);
        roll_value.setEnabled(false);
        Button details = findViewById(R.id.details_button);
        details.setEnabled(false);
        ImageButton settings = findViewById(R.id.settings_button);
        settings.setEnabled(true);

        //--------------------------------------------------
        //After selecting a saved roll
        //--------------------------------------------------

        ArrayList<String> MainText = new ArrayList<>();

        ActivityResultLauncher<Intent> arl = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
                result -> {
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
                            main_view.setText(b.getString("Display"));
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
                            plus_button.setImageResource(R.drawable.plus);
                            minus_button.setImageResource(R.drawable.minus);
                            minus_button.setEnabled(true);
                            clear_last.setEnabled(true);
                            clear_all.setEnabled(true);
                            save_roll.setEnabled(true);
                            roll_value.setEnabled(true);

                        }
                    }
                    else if (result.getResultCode() == 3) {

                        setTheme(R.style.DarkMode);
                        setTheme(R.style.Base_Theme_RollPlay);

                        recreate();
                    }
                });

        //--------------------------------------------------
        //Listeners:
        //--------------------------------------------------

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
            if (!selected.equals(new_selected)) {
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
                rolls_bar.setProgress(1);
                bar_display.setText("1");
                add_button.setEnabled(true);
            }
            else {
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
                bar_display.setText("0");
                add_button.setEnabled(false);
            }
        };

        d4.setOnClickListener(DiceButtons);
        d6.setOnClickListener(DiceButtons);
        d8.setOnClickListener(DiceButtons);
        d10.setOnClickListener(DiceButtons);
        d12.setOnClickListener(DiceButtons);
        d20.setOnClickListener(DiceButtons);

        rolls_bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                bar_display.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        View.OnClickListener Plus_Minus_Buttons = v -> {
            int id = v.getId();
            if (id == R.id.plus_button) {
                MainText.add("+");
                main_view.append(" + ");
            } else if (id == R.id.minus_button) {
                MainText.add("-");
                main_view.append(" - ");
            }
            plus_button.setEnabled(false);
            plus_button.setImageResource(R.drawable.plus_disabled);
            minus_button.setEnabled(false);
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
            roll_value.setEnabled(false);
        };

        plus_button.setOnClickListener(Plus_Minus_Buttons);
        minus_button.setOnClickListener(Plus_Minus_Buttons);

        View.OnClickListener add_button_listener = v -> {
            MainText.add(bar_display.getText().toString());
            MainText.add(selected);
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
            rolls_bar.setEnabled(false);
            rolls_bar.setProgress(1);
            bar_display.setText("0");
            plus_button.setEnabled(true);
            plus_button.setImageResource(R.drawable.plus);
            minus_button.setEnabled(true);
            minus_button.setImageResource(R.drawable.minus);
            clear_last.setEnabled(true);
            clear_all.setEnabled(true);
            save_roll.setEnabled(true);
            roll_value.setEnabled(true);
        };

        add_button.setOnClickListener(add_button_listener);

        View.OnClickListener ClearSelectionsButtons = v -> {
            int id = v.getId();
            if (id == R.id.clear_last_button) {
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
                    plus_button.setImageResource(R.drawable.plus);
                    minus_button.setEnabled(true);
                    minus_button.setImageResource(R.drawable.minus);
                    save_roll.setEnabled(true);
                    roll_value.setEnabled(true);
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
                    plus_button.setImageResource(R.drawable.plus_disabled);
                    minus_button.setEnabled(false);
                    minus_button.setImageResource(R.drawable.minus_disabled);
                    save_roll.setEnabled(false);
                    roll_value.setEnabled(false);
                }
                main_view.setText("");
                String text;
                if (MainText.size() > 0) {
                    for (int i = 0; i < MainText.size(); i++) {
                        text = MainText.get(i);
                        if (text.equals("+") || text.equals("-"))
                            main_view.append(" " + text + " ");
                        else
                            main_view.append(text);
                    }
                }
                else {
                    clear_last.setEnabled(false);
                    clear_all.setEnabled(false);
                }
            }
            else {
                MainText.clear();
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
                plus_button.setImageResource(R.drawable.plus_disabled);
                minus_button.setEnabled(false);
                minus_button.setImageResource(R.drawable.minus_disabled);
                clear_last.setEnabled(false);
                clear_all.setEnabled(false);
                details.setEnabled(false);
                save_roll.setEnabled(false);
                modifier_input.setText("");
                modifier_input.setEnabled(true);
                roll_value.setEnabled(false);
            }
            selected = "none";
            use_saved.setEnabled(true);
            rolls_bar.setEnabled(false);
            bar_display.setText("0");
            add_button.setEnabled(false);
        };

        clear_last.setOnClickListener(ClearSelectionsButtons);
        clear_all.setOnClickListener(ClearSelectionsButtons);

        ArrayList<Integer> d4_rolls = new ArrayList<>();
        ArrayList<Integer> d6_rolls = new ArrayList<>();
        ArrayList<Integer> d8_rolls = new ArrayList<>();
        ArrayList<Integer> d10_rolls = new ArrayList<>();
        ArrayList<Integer> d12_rolls = new ArrayList<>();
        ArrayList<Integer> d20_rolls = new ArrayList<>();

        View.OnClickListener RollButton = v -> {
            int result = 0;
            int i = 0;
            if (recentRolls.size() == 4)
                recentRolls.remove(0);
            recentRolls.add(main_view.getText().toString());
            String operation = "addition";
            if (MainText.get(0).equals("-")) {
                operation = "subtraction";
                i++;
            }
            while (i+1 < MainText.size()) {
                String character = MainText.get(i+1);
                int dice_sum = 0;
                int random_value;
                Random r = new Random();
                switch (character) {
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
            if (!modifier_input.getText().toString().equals("")) {
                result += Integer.parseInt(modifier_input.getText().toString());
            }
            String string = "Result:\n" + result;
            main_view.setText(string);
            plus_button.setEnabled(false);
            plus_button.setImageResource(R.drawable.plus_disabled);
            minus_button.setEnabled(false);
            minus_button.setImageResource(R.drawable.minus_disabled);
            clear_last.setEnabled(false);
            details.setEnabled(true);
            use_saved.setEnabled(false);
            modifier_input.setEnabled(false);
            roll_value.setEnabled(false);
        };
        roll_value.setOnClickListener(RollButton);

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

        View.OnClickListener useSavedButton = v -> {
            Intent intent = new Intent(MainActivity.this, UseSavedActivity.class);
            arl.launch(intent);
        };

        use_saved.setOnClickListener(useSavedButton);

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

        View.OnClickListener settingsButton = v -> {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            Bundle b = new Bundle();
            b.putStringArrayList("Recent Rolls", recentRolls);
            intent.putExtras(b);
            arl.launch(intent);
        };

        settings.setOnClickListener(settingsButton);
    }
}