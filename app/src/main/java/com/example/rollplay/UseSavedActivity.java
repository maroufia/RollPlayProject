package com.example.rollplay;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

public class UseSavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_saved);

        RecyclerView recyclerView = findViewById(R.id.saved_rolls);
        Button confirmBtn = findViewById(R.id.confirmBtn);
        SearchView search = findViewById(R.id.searchView);
        confirmBtn.setEnabled(false);
        Button backBtn = findViewById(R.id.backBtn2);

        LinearLayoutManager llm;
        GridLayoutManager glm;
        if (this.getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {
            llm = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(llm);
        }
        else {
            glm = new GridLayoutManager(this, 3);
            recyclerView.setLayoutManager(glm);
        }

        DBHandler db = new DBHandler(this, null, null, 1);
        SavedAdapter adapter = new SavedAdapter(db, confirmBtn);
        recyclerView.setAdapter(adapter);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.update(db, newText);
                return true;
            }
        });

        confirmBtn.setOnClickListener( v -> {
            Intent intent = new Intent(UseSavedActivity.this, MainActivity.class);
            Bundle bundle = new Bundle();
            bundle.putStringArray("Main Text", adapter.getSelectedMainText());
            bundle.putString("Modifier", adapter.getSelectedModifier());
            bundle.putIntArray("Dice", adapter.getRightSelectedDice());
            bundle.putString("Display", adapter.getSelectedDisplay());
            intent.putExtras(bundle);
            setResult(2, intent);
            finish();
        });

        backBtn.setOnClickListener( v -> {
            Intent intent = new Intent(UseSavedActivity.this, MainActivity.class);
            setResult(1, intent);
            finish();
        });
    }
}