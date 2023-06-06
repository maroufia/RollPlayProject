package com.example.rollplay;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Objects;

/*
Sub-activity of MainActivity
Gets roll information and the user can choose to save it on an SQLite Database for future use
 */
public class SaveActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Bundle bundle = getIntent().getExtras(); //The bundle containing the roll information
        final ArrayList<String> Roll_Text = bundle.getStringArrayList("Roll"); // or other values

        EditText RollName = findViewById(R.id.inputName);
        TextView RollText = findViewById(R.id.textRoll);
        TextView ErrorText = findViewById(R.id.error_text);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button backBtn = findViewById(R.id.backBtn);

        /*
        Makes a readable String out of the roll info
         */
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

        /*
        Listener for the Save button
        Calls the saveRoll method
        If the method returns 1 or 2 displays the appropriate error message
        Else the sub-activity ends successfully
         */
        View.OnClickListener saveBtnListener = v -> {
            int flag = saveRoll(RollName, Roll_Text);
            switch (flag) {
                case 0:
                    Intent intent = new Intent(SaveActivity.this, MainActivity.class);
                    setResult(3, intent);
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

        /*
        Listener for the Back button
        Ends the sub-activity
         */
        View.OnClickListener backBtnListener = v -> {
            Intent intent = new Intent(SaveActivity.this, MainActivity.class);
            setResult(3, intent);
            finish();
        };

        backBtn.setOnClickListener(backBtnListener);
    }

    /*
    Saves the roll in the SQLite Database after the user inputs a name for it
    If the input has either no characters or already exists in the Database, the method returns an error code (1 or 2)
     */
    public int saveRoll(EditText RollName, ArrayList<String> Roll_Text) {
        DBHandler handler = new DBHandler(this, null, null, 1);
        String name = RollName.getText().toString().toLowerCase();
        if (name.equals("")) //User input has no characters
            return 1;
        else {
            Roll found = handler.findRoll(name);
            if (found != null) //User input already exists in the Database
                return 2;
            else {
                int d4 = 0;
                int d6 = 0;
                int d8 = 0;
                int d10 = 0;
                int d12 = 0;
                int d20 = 0;
                int mod = 0;
                int num_of_dice;
                int loop_start = 1;
                String operator = "+";
                if (Roll_Text.get(0).equals("-")) {
                    operator = "-";
                    num_of_dice = Integer.parseInt(Roll_Text.get(1));
                    loop_start = 2;
                }
                else
                    num_of_dice = Integer.parseInt(Roll_Text.get(0));
                for (int i = loop_start; i < Roll_Text.size(); i++) {
                    String symbol = Roll_Text.get(i);
                    switch (symbol) {
                        case "d4":
                            if (operator.equals("+"))
                                d4 = num_of_dice;
                            else
                                d4 = (-1) * num_of_dice;
                            break;
                        case "d6":
                            if (operator.equals("+"))
                                d6 = num_of_dice;
                            else
                                d6 = (-1) * num_of_dice;
                            break;
                        case "d8":
                            if (operator.equals("+"))
                                d8 = num_of_dice;
                            else
                                d8 = (-1) * num_of_dice;
                            break;
                        case "d10":
                            if (operator.equals("+"))
                                d10 = num_of_dice;
                            else
                                d10 = (-1) * num_of_dice;
                            break;
                        case "d12":
                            if (operator.equals("+"))
                                d12 = num_of_dice;
                            else
                                d12 = (-1) * num_of_dice;
                            break;
                        case "d20":
                            if (operator.equals("+"))
                                d20 = num_of_dice;
                            else
                                d20 = (-1) * num_of_dice;
                            break;
                        case "mod":
                            if (Roll_Text.get(i - 1).equals("-"))
                                mod = (-1) * Integer.parseInt(Roll_Text.get(i + 1));
                            else
                                mod = Integer.parseInt(Roll_Text.get(i + 1));
                            i++;
                            break;
                        case "+":
                            operator = "+";
                            break;
                        case "-":
                            operator = "-";
                            break;
                        default:
                            num_of_dice = Integer.parseInt(symbol);
                            break;
                    }
                }
                Roll new_roll = new Roll(name, d4, d6, d8, d10, d12, d20, mod);
                Log.d("D4", Integer.toString(d4));
                Log.d("D6", Integer.toString(d6));
                Log.d("D8", Integer.toString(d8));
                Log.d("D10", Integer.toString(d10));
                Log.d("D12", Integer.toString(d12));
                Log.d("D20", Integer.toString(d20));
                handler.addRoll(new_roll); //The Roll is saved
                return 0;
            }
        }
    }
}