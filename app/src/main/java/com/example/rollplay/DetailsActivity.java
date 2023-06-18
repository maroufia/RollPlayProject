package com.example.rollplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Objects;

/*
The Details activity is accessible through the app's main activity by pressing the Details button after rolling.
It shows each roll separately for every type of dice. Also has the ability to sort/unsort the results
for more clear viewing.
 */
public class DetailsActivity extends AppCompatActivity {

    private boolean alternate_sort = false; //Whether or not the results need to be sorted or not

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Objects.requireNonNull(getSupportActionBar()).hide();
        //Checks if Dark Mode or Light Mode is enabled and sets its theme accordingly.
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            setTheme(R.style.DarkMode);
        else
            setTheme(R.style.Base_Theme_RollPlay);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Button back_button = findViewById(R.id.back_button); //The screen's back button

        Button sort_button = findViewById(R.id.sort_button); //On press sorts the results. If pressed again it arranges them
                                                             //in the order they were rolled.
        Bundle bundle = getIntent().getExtras(); //The results of the last roll
        if (bundle != null) {
            Details_Str(bundle, false); //Call to Details_Str to arrange the dice in the order they were rolled.
        }

        //The back button, which when pressed returns control to the main activity.
        back_button.setOnClickListener(v -> {
            Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
            setResult(1, intent);
            finish();
        });

        /*
        The sort button which checks if the results are currently sorted or not and calls Details_Str to either
        sort or rearrange them in the order they were rolled.
         */
        sort_button.setOnClickListener(v -> {
            if (bundle != null) {
                alternate_sort = !alternate_sort;
                Details_Str(bundle, alternate_sort);
                if (alternate_sort)
                    sort_button.setText("Unsort");
                else
                    sort_button.setText("Sort");
            }
        });
    }

    /*
    Details_Str is the function responsible for the layout of the TextViews in which the dice are arranged.
    It receives the Bundle b which is the dice rolled and their results, as well as the boolean variable
    sort. When sort is true it sorts the results of each dice type in ascending order separately. When it is
    set to false it instead rearranges them to the order they were rolled. Also the TextViews are arranged so
    that each type of dice rolled has equal space dynamically.
     */
    private void Details_Str(Bundle b, boolean sort){
        StringBuilder details = new StringBuilder(); // The string that holds all the rolls for each type of dice.
        TextView d4_det = findViewById(R.id.details_d4), d6_det = findViewById(R.id.details_d6),
                d8_det = findViewById(R.id.details_d8), d10_det = findViewById(R.id.details_d10),
                d12_det = findViewById(R.id.details_d12), d20_det = findViewById(R.id.details_d20);

        if (b != null) {
            //Arraylists with the results of each separate type of dice rolled.
            ArrayList<Integer> d4_rolls = new ArrayList<>(b.getIntegerArrayList("d4"));
            ArrayList<Integer> d6_rolls = new ArrayList<>(b.getIntegerArrayList("d6"));
            ArrayList<Integer> d8_rolls = new ArrayList<>(b.getIntegerArrayList("d8"));
            ArrayList<Integer> d10_rolls = new ArrayList<>(b.getIntegerArrayList("d10"));
            ArrayList<Integer> d12_rolls = new ArrayList<>(b.getIntegerArrayList("d12"));
            ArrayList<Integer> d20_rolls = new ArrayList<>(b.getIntegerArrayList("d20"));

            //If sort is true then it sorts each of the Arraylists.
            if (sort){
                d4_rolls.sort(null); d6_rolls.sort(null); d8_rolls.sort(null);
                d10_rolls.sort(null); d12_rolls.sort(null); d20_rolls.sort(null);
            }

            d4_det.setText(""); //At first the textView is empty.
            if (d4_rolls.size() > 0) { //Checks if this type of dice was rolled.
                //Sets the proper weights for the View so that the Views are dynamically arranged.
                d4_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D4: ");
                for (int i = 0; i < d4_rolls.size(); i++)
                    details.append(d4_rolls.get(i)).append(", "); //Appends and spaces each result for this type of dice to the string.
                details.delete(details.length()-2, details.length()-1); //Deletes the last ", " added by the loop.
                d4_det.append(details);
                details = new StringBuilder(); //After setting the TextView the StringBuilder is rest for use on the next dice type.
            }

            //The above process repeats 5 more times, once for each type of dice.

            d6_det.setText("");
            if (d6_rolls.size() > 0) {
                d6_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D6: ");
                for (int i = 0; i < d6_rolls.size(); i++)
                    details.append(d6_rolls.get(i)).append(", ");
                details.delete(details.length()-2, details.length()-1);
                d6_det.append(details);
                details = new StringBuilder();
            }


            d8_det.setText("");
            if (d8_rolls.size() > 0) {
                d8_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D8: ");
                for (int i = 0; i < d8_rolls.size(); i++)
                    details.append(d8_rolls.get(i)).append(", ");
                details.delete(details.length()-2, details.length()-1);
                d8_det.append(details);
                details = new StringBuilder();
            }


            d10_det.setText("");
            if (d10_rolls.size() > 0) {
                d10_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D10: ");
                for (int i = 0; i < d10_rolls.size(); i++)
                    details.append(d10_rolls.get(i)).append(", ");
                details.delete(details.length()-2, details.length()-1);
                d10_det.append(details);
                details = new StringBuilder();
            }


            d12_det.setText("");
            if (d12_rolls.size() > 0) {
                d12_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D12: ");
                for (int i = 0; i < d12_rolls.size(); i++)
                    details.append(d12_rolls.get(i)).append(", ");
                details.delete(details.length()-2, details.length()-1);
                d12_det.append(details);
                details = new StringBuilder();
            }


            d20_det.setText("");
            if (d20_rolls.size() > 0) {
                d20_det.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 0, 1f));
                details.append("D20: ");
                for (int i = 0; i < d20_rolls.size(); i++)
                    details.append(d20_rolls.get(i)).append(", ");
                details.delete(details.length()-2, details.length()-1);
                d20_det.append(details);
            }
        }
    }
}