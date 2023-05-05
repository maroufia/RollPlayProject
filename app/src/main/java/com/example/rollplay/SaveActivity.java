package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class SaveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save);

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> Roll = new ArrayList<>(); // or other values
        if(bundle != null)
            Roll = bundle.getStringArrayList("Roll");

        TextView RollText = findViewById(R.id.textRoll);
        Button saveBtn = findViewById(R.id.saveBtn);
        Button backBtn = findViewById(R.id.backBtn);

        RollText.setText("Roll: ");
        for (int i=0; i < Roll.size(); i++)
            RollText.append(Roll.get(i));

        View.OnClickListener saveBtnListener = v -> {

        };

        saveBtn.setOnClickListener(saveBtnListener);

        View.OnClickListener backBtnListener = v -> {
            Intent intent = new Intent(SaveActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        };

        backBtn.setOnClickListener(backBtnListener);
    }
}