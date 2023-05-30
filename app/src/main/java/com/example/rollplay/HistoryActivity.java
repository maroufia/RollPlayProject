package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.helper.widget.Carousel;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.TextView;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView history1 = findViewById(R.id.roll1);
        TextView history2 = findViewById(R.id.roll2);
        TextView history3 = findViewById(R.id.roll3);
        TextView history4 = findViewById(R.id.roll4);

        Button back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, Settings.class);
            startActivity(intent);
            finish();
        });

    }
}