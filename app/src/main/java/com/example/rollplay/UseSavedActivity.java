package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SearchView;

public class UseSavedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_saved);

        RecyclerView recyclerView = findViewById(R.id.saved_rolls);
        Button confirmBtn = findViewById(R.id.confirmBtn);
        SearchView search = findViewById(R.id.searchView);
        confirmBtn.setEnabled(false);
        Button backBtn = findViewById(R.id.backBtn2);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

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
            bundle.putStringArrayList("Main Text", adapter.getSelectedMainText());
            bundle.putString("Modifier", adapter.getSelectedModifier());
            bundle.putIntArray("Dice", adapter.getRightSelectedDice());
            bundle.putString("Display", adapter.getSelectedDisplay());
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        });

        backBtn.setOnClickListener( v -> {
            Intent intent = new Intent(UseSavedActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}