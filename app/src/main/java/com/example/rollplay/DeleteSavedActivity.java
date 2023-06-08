package com.example.rollplay;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import java.util.Objects;

/*
Sub-activity of Settings
Presents a RecyclerView with all the rolls saved in the Database
The user can also search for a roll using a SearchView
If the user selects a roll its info gets passed to the DeleteActivity
 */
public class DeleteSavedActivity extends AppCompatActivity {

    //Checks if Dark Mode or Light Mode is enabled and sets its theme accordingly.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Objects.requireNonNull(getSupportActionBar()).hide();

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_saved);

        RecyclerView recyclerView = findViewById(R.id.saved_rolls2);
        SearchView search = findViewById(R.id.searchView2);
        Button backBtn = findViewById(R.id.backBtn3);

        /*
        If the device is on Portrait Mode, the RecyclerView is displayed vertically
        Else if the device is on Landscape Mode, the RecyclerView is displayed like a horizontal grid
         */
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
        SavedAdapter adapter = new SavedAdapter(db, this);
        recyclerView.setAdapter(adapter);

        /*
        Listener for the SearchView
        When the view gets updated, the RecyclerView gets updated as well based on the user's query
        */
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

        /*
        Listener for the Back button
        Finishes the sub-activity
         */
        View.OnClickListener bckBtnListener = v -> {
            Intent intent = new Intent(DeleteSavedActivity.this, Settings.class);
            setResult(1, intent);
            finish();
        };

        backBtn.setOnClickListener(bckBtnListener);

    }
}