package com.example.rollplay;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Objects;

public class SaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.DarkMode);

        } else {
            setTheme(R.style.Base_Theme_RollPlay);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Bundle bundle = getIntent().getExtras();
        final ArrayList<String> Roll_Text = bundle.getStringArrayList("Roll"); // or other values

        EditText RollName = findViewById(R.id.inputName);
        TextView RollText = findViewById(R.id.textRoll);
        TextView ErrorText = findViewById(R.id.error_text);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button backBtn = findViewById(R.id.backBtn);

        RollText.setText("Roll: ");
        for (int i=0; i < Roll_Text.size(); i++) {
            String symbol = Roll_Text.get(i);
            switch (symbol) {
                case "+":
                    RollText.append(" + ");
                    break;
                case "-":
                    RollText.append(" - ");
                    break;
                case "mod":
                    int modifier = Integer.parseInt(Roll_Text.get(i+1));
                    if (modifier > 0)
                        RollText.append(" + " + modifier);
                    else
                        RollText.append(" - " + (-1 * modifier));
                    i++;
                    break;
                default:
                    RollText.append(symbol);
                    break;
            }
        }

        View.OnClickListener saveBtnListener = v -> {
            int flag = saveRoll(RollName, Roll_Text);
            switch (flag) {
                case 0:
                    Intent intent = new Intent(SaveActivity.this, MainActivity.class);
                    setResult(1, intent);
                    finish();
                    break;
                case 1:
                    ErrorText.setText("Please enter a name");
                    break;
                case 2:
                    ErrorText.setText("This name already exists");
            }
        };

        saveBtn.setOnClickListener(saveBtnListener);

        View.OnClickListener backBtnListener = v -> {
            Intent intent = new Intent(SaveActivity.this, MainActivity.class);
            setResult(1, intent);
            finish();
        };

        backBtn.setOnClickListener(backBtnListener);
    }

    public int saveRoll(EditText RollName, ArrayList<String> Roll_Text) {
        DBHandler handler = new DBHandler(this, null, null, 1);
        String name = RollName.getText().toString().toLowerCase();
        if (name.equals(""))
            return 1;
        else {
            Roll found = handler.findRoll(name);
            if (found != null)
                return 2;
            else {
                int d4 = 0; int d6 = 0; int d8 = 0; int d10 = 0; int d12 = 0; int d20 = 0; int mod = 0;
                int num_of_dice = Integer.parseInt(Roll_Text.get(0));
                for (int i = 1; i < Roll_Text.size(); i++) {
                    String symbol = Roll_Text.get(i);
                    switch (symbol) {
                        case "d4":
                            d4 = num_of_dice;
                            break;
                        case "d6":
                            d6 = num_of_dice;
                            break;
                        case "d8":
                            d8 = num_of_dice;
                            break;
                        case "d10":
                            d10 = num_of_dice;
                            break;
                        case "d12":
                            d12 = num_of_dice;
                            break;
                        case "d20":
                            d20 = num_of_dice;
                            break;
                        case "mod":
                            if (Roll_Text.get(i-1).equals("-"))
                                mod = (-1) * Integer.parseInt(Roll_Text.get(i+1));
                            else
                                mod = Integer.parseInt(Roll_Text.get(i+1));
                            i++;
                            break;
                        case "+":
                        case "-":
                            break;
                        default:
                            num_of_dice = Integer.parseInt(symbol);
                            break;
                    }
                }
                Roll new_roll = new Roll(name, d4, d6, d8, d10, d12, d20, mod);
                handler.addRoll(new_roll);
                return 0;
            }
        }
    }
}