package com.example.rollplay;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import java.util.ArrayList;
import java.util.Objects;

/*
The History activity which shows the last four rolls of this session. The one at the top being
the most recent.
 */

public class HistoryActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();
        //Checks if Dark Mode or Light Mode is enabled and sets its theme accordingly.
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //The four different TextViews for each of the four last rolls of the session.
        TextView history1 = findViewById(R.id.roll1);
        TextView history2 = findViewById(R.id.roll2);
        TextView history3 = findViewById(R.id.roll3);
        TextView history4 = findViewById(R.id.roll4);

        final ArrayList<String> recentRolls; //Arraylist that holds the number and type of dice rolled.
        final ArrayList<String> recentRollResults; //Arraylist that holds the results of each dice rolled.
        final ArrayList<String> recentModifiers; //Arraylist that holds the flat modifiers of the last rolls.
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) { //Fills the above Arraylists with the contents of bundle.
            recentRolls = bundle.getStringArrayList("Recent Rolls");
            recentRollResults = bundle.getStringArrayList("Recent Roll Results");
            recentModifiers = bundle.getStringArrayList("Recent Modifiers");
        }
        else {
            recentRolls = null;
            recentRollResults = null;
            recentModifiers = null;
        }

        String history_text; //String that is used to fill in each TextView.
        if (recentRolls != null)
            /*
            Checks how many rolls have been rolled this session and fills the Views accordingly
            with the topmost being the most recent roll.
             */
            switch (recentRolls.size()) {
                case 1: //There has been only one roll this session.
                    //Checks whether or not the user has inputted any flat modifiers to layout accordingly.
                    if (recentModifiers.get(0).equals("0")){ //If there are no modifiers.
                        history_text = recentRolls.get(0) + "\nResult: " + recentRollResults.get(0);
                        history1.setText(history_text);
                    } else { //If there are modifiers.
                        history_text = recentRolls.get(0)  + recentModifiers.get(0) + "\nResult: " + recentRollResults.get(0);
                        history1.setText(history_text);
                    }
                    break;
                    /*
                    This process is repeated for two, three, and four or more rolls, setting the most recent roll as the
                    top one.
                     */
                case 2: //There have been two rolls this session.
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
                case 3: //There have been three rolls this session.
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
                case 4: //There have been four or more rolls this session. If there are more than four then the user can only see the
                        //four most recent rolls of the session.
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


        Button back = findViewById(R.id.back); //This activity's back button.

        //When pressed returns control to the Settings Activity.
        back.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, Settings.class);
            setResult(1, intent);
            finish();
        });

    }

}