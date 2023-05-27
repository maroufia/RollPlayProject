package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class Details extends AppCompatActivity {

    private boolean alternate_sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Button back_button = findViewById(R.id.back_button);

        Button sort_button = findViewById(R.id.sort_button);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Details_Str(bundle, false);
        }

        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(Details.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        sort_button.setOnClickListener(v -> {
            if (bundle != null) {
                alternate_sort = !alternate_sort;
                Details_Str(bundle, alternate_sort);
            }
        });
    }

    private void Details_Str(Bundle b, boolean sort){
        StringBuilder details = new StringBuilder();
        TextView d4_det = findViewById(R.id.details_d4), d6_det = findViewById(R.id.details_d6),
                d8_det = findViewById(R.id.details_d8), d10_det = findViewById(R.id.details_d10),
                d12_det = findViewById(R.id.details_d12), d20_det = findViewById(R.id.details_d20);

        if (b != null) {
            ArrayList<Integer> d4_rolls = new ArrayList<>(b.getIntegerArrayList("d4"));
            ArrayList<Integer> d6_rolls = new ArrayList<>(b.getIntegerArrayList("d6"));
            ArrayList<Integer> d8_rolls = new ArrayList<>(b.getIntegerArrayList("d8"));
            ArrayList<Integer> d10_rolls = new ArrayList<>(b.getIntegerArrayList("d10"));
            ArrayList<Integer> d12_rolls = new ArrayList<>(b.getIntegerArrayList("d12"));
            ArrayList<Integer> d20_rolls = new ArrayList<>(b.getIntegerArrayList("d20"));

            if (sort){
                d4_rolls.sort(null); d6_rolls.sort(null); d8_rolls.sort(null);
                d10_rolls.sort(null); d12_rolls.sort(null); d20_rolls.sort(null);
            }

            d4_det.setText("");
            if (d4_rolls.size() > 0) {
                d4_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D4: ");
                for (int i = 0; i < d4_rolls.size(); i++)
                    details.append(d4_rolls.get(i)).append(", ");
                details.append("\b\b");
                d4_det.append(details);
                details = new StringBuilder();
            }


            d6_det.setText("");
            if (d6_rolls.size() > 0) {
                d6_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D6: ");
                for (int i = 0; i < d6_rolls.size(); i++)
                    details.append(d6_rolls.get(i)).append(", ");
                details.append("\b\b");
                d6_det.append(details);
                details = new StringBuilder();
            }


            d8_det.setText("");
            if (d8_rolls.size() > 0) {
                d8_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D8: ");
                for (int i = 0; i < d8_rolls.size(); i++)
                    details.append(d8_rolls.get(i)).append(", ");
                details.append("\b\b");
                d8_det.append(details);
                details = new StringBuilder();
            }


            d10_det.setText("");
            if (d10_rolls.size() > 0) {
                d10_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D10: ");
                for (int i = 0; i < d10_rolls.size(); i++)
                    details.append(d10_rolls.get(i)).append(", ");
                details.append("\b\b");
                d10_det.append(details);
                details = new StringBuilder();
            }


            d12_det.setText("");
            if (d12_rolls.size() > 0) {
                d12_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D12: ");
                for (int i = 0; i < d12_rolls.size(); i++)
                    details.append(d12_rolls.get(i)).append(", ");
                details.append("\b\b");
                d12_det.append(details);
                details = new StringBuilder();
            }


            d20_det.setText("");
            if (d20_rolls.size() > 0) {
                d20_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D20: ");
                for (int i = 0; i < d20_rolls.size(); i++)
                    details.append(d20_rolls.get(i)).append(", ");
                details.append("\b\b");
                d20_det.append(details);
            }
        }
    }
}