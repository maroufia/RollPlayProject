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

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().hide();
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        TextView history1 = findViewById(R.id.roll1);
        TextView history2 = findViewById(R.id.roll2);
        TextView history3 = findViewById(R.id.roll3);
        TextView history4 = findViewById(R.id.roll4);

        final ArrayList<String> recentRolls;
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
            recentRolls = bundle.getStringArrayList("Recent Rolls"); // or other values
        else
            recentRolls = null;


        if (recentRolls != null)
            switch (recentRolls.size()) {
                case 1:
                    history1.setText(recentRolls.get(0));
                    break;
                case 2:
                    history1.setText(recentRolls.get(1));
                    history2.setText(recentRolls.get(0));
                    break;
                case 3:
                    history1.setText(recentRolls.get(2));
                    history2.setText(recentRolls.get(1));
                    history3.setText(recentRolls.get(0));
                    break;
                case 4:
                    history1.setText(recentRolls.get(3));
                    history2.setText(recentRolls.get(2));
                    history3.setText(recentRolls.get(1));
                    history4.setText(recentRolls.get(0));
                    break;
            }


        Button back = findViewById(R.id.back);

        back.setOnClickListener(v -> {
            Intent intent = new Intent(HistoryActivity.this, Settings.class);
            Bundle b = new Bundle();
            b.putStringArrayList("Recent Rolls", recentRolls);
            intent.putExtras(b);
            startActivity(intent);
            finish();
        });

    }
}