package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

/*
Started from SavedAdapter as soon as the user selects a roll
The name and roll details are displayed and the user can either press the Delete Button to delete the roll from the database
or the Back Button to return to the DeleteSavedActivity
 */
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

        TextView rollName = findViewById(R.id.textName1);
        TextView roll = findViewById(R.id.textRoll1);
        Button delete = findViewById(R.id.deleteBtn);
        Button back = findViewById(R.id.backBtn4);

        /*
        Roll info is set with the Bundle sent by the SavedAdapter
         */
        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("Name");
        String textString = "Name: " + name.substring(0,1).toUpperCase() + name.substring(1);
        rollName.setText(textString);
        String textRoll = "Roll: " + bundle.getString("Roll");
        roll.setText(textRoll);

        /*
        Buttons' listener
        If the Delete Button is pressed the roll gets deleted from the database
        This Activity ends and the DeleteSavedActivity starts again
         */
        View.OnClickListener buttons_listener = v -> {
            if (v.getId() == R.id.deleteBtn) {
                DBHandler dbh = new DBHandler(this, null, null, 1);
                dbh.removeRoll(name);
            }
            Intent intent = new Intent(DeleteActivity.this, DeleteSavedActivity.class);
            startActivity(intent);
            finish();
        };

        delete.setOnClickListener(buttons_listener);
        back.setOnClickListener(buttons_listener);
    }
}