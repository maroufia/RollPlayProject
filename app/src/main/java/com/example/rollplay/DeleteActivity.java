package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

        TextView rollName = findViewById(R.id.textName1);
        TextView roll = findViewById(R.id.textRoll1);
        Button delete = findViewById(R.id.deleteBtn);
        Button back = findViewById(R.id.backBtn4);

        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("Name");
        String textString = "Name: " + name.substring(0,1).toUpperCase() + name.substring(1);
        rollName.setText(textString);
        String textRoll = "Roll: " + bundle.getString("Roll");
        roll.setText(textRoll);

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