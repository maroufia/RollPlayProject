package com.example.rollplay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/*
Class that determines the RecyclerAdapter's behaviour for the UseSavedActivity and DeleteSavedActivity
 */
public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private Button confirmBtn; //Confirm Button of UseSavedActivity (if the class is used for DeleteSavedActivity, this variable is null)
    private final Context context; //Instance of UseSavedActivity or DeleteSavedActivity
    private String[] names; //Names of all saved rolls
    private int[][] selectedDice; //Array that holds what die each roll uses
    private int[] rightSelectedDice; //The die that correspond to the user selected saved roll
    private ArrayList<String[]> MainText; //Each roll's info
    private String[] SelectedMainText; //The user selected roll's info
    private String[] modifier; //Each roll's modifier
    private String SelectedModifier; //The user selected roll's modifier
    private String[] display; //Each roll's displayed text
    private String SelectedDisplay; //The user selected roll's displayed text

    /*
    Parameter DBHandler db: Instance of the class that handles the SQLite Database
    Parameter Context context: Instance of UseSavedActivity or DeleteSavedActivity
     */
    public SavedAdapter(DBHandler db, Context context) {
        this.context = context;
        if (context instanceof UseSavedActivity) //Confirm button gets initialized if context is an instance of UseSavedActivity
            this.confirmBtn = (Button) ((Activity) context).findViewById(R.id.confirmBtn);
        Roll[] rolls = db.getAllRolls(); //Gets all rolls saved in the database
        names = new String[rolls.length];
        selectedDice = new int[rolls.length][6];
        display = new String[rolls.length];
        MainText = new ArrayList<>();
        modifier = new String[rolls.length];
        initData(rolls); //Initializes all variables for each roll
        db.close();
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_roll_layout, parent, false);
        return new ViewHolder(v);
    }

    /*
    Called to display the data of every saved roll on the Adapter
    Each roll is displayed on a Button (Name and roll info)
     */
    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {

        String text = "Name: " + names[position].substring(0,1).toUpperCase() + names[position].substring(1) + "\nRoll: " + display[position];
        holder.UseBtn.setText(text);

        /*
        Called when the user presses one of the rolls' Buttons
        If the Adapter was created as part of the UseSavedActivity (confirmBtn not null) then it enables the Confirm Button and the user can press it
        to confirm his selection
        Otherwise (Adapter created as part of the DeleteSavedActivity), when the user selects a roll the DeleteActivity will start and
        the DeleteSavedActivity will be finished
        In both cases the necessary roll info is acquired with the user's selection
         */
        holder.UseBtn.setOnClickListener (v -> {
            if (confirmBtn != null) {
                SelectedMainText = MainText.get(holder.getAbsoluteAdapterPosition());
                SelectedModifier = modifier[holder.getAbsoluteAdapterPosition()];
                rightSelectedDice = selectedDice[holder.getAbsoluteAdapterPosition()];
                SelectedDisplay = display[holder.getAbsoluteAdapterPosition()];
                if (!SelectedModifier.equals(""))
                    SelectedDisplay = SelectedDisplay.substring(0, SelectedDisplay.length() - 4);
                confirmBtn.setEnabled(true);
            }
            else {
                Bundle bundle = new Bundle();
                bundle.putString("Name", names[holder.getAbsoluteAdapterPosition()]);
                bundle.putString("Roll", display[holder.getAbsoluteAdapterPosition()]);
                Intent intent = new Intent(context, DeleteActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    /*
    For each roll, all necessary variables get initialized (names, MainText, selectedDice, display, modifier)
     */
    private void initData(Roll[] rolls) {
        ArrayList<String> text = new ArrayList<>();
        for (int i=0; i < rolls.length; i++) {
            int[] selectedDice_ = {1, 1, 1, 1, 1, 1};
            names[i] = rolls[i].getName();
            String string = "";
            int d4 = rolls[i].getD4();
            if (d4 != 0) {
                selectedDice_[0] = 0;
                if (d4 > 0) {
                    string += (d4 + "d4");
                    text.add(Integer.toString(d4));
                }
                else {
                    string += (" - " + (-1) * d4 + "d4");
                    text.add("-");
                    text.add(Integer.toString((-1) * d4));
                }
                text.add("d4");
            }
            int d6 = rolls[i].getD6();
            if (d6 != 0) {
                selectedDice_[1] = 0;
                if (d6 > 0) {
                    if (!text.isEmpty()) {
                        string += (" + " + d6 + "d6");
                        text.add("+");
                    } else
                        string += (d6 + "d6");
                    text.add(Integer.toString(d6));
                }
                else {
                    string += (" - " + (-1) * d6 + "d6");
                    text.add("-");
                    text.add(Integer.toString((-1) * d6));
                }
                text.add("d6");
            }
            int d8 = rolls[i].getD8();
            if (d8 != 0) {
                selectedDice_[2] = 0;
                if (d8 > 0) {
                    if (!text.isEmpty()) {
                        string += (" + " + d8 + "d8");
                        text.add("+");
                    } else
                        string += (d8 + "d8");
                    text.add(Integer.toString(d8));
                }
                else {
                    string += (" - " + (-1) * d8 + "d8");
                    text.add("-");
                    text.add(Integer.toString((-1) * d8));
                }
                text.add("d8");
            }
            int d10 = rolls[i].getD10();
            if (d10 != 0) {
                selectedDice_[3] = 0;
                if (d10 > 0) {
                    if (!text.isEmpty()) {
                        string += (" + " + d10 + "d10");
                        text.add("+");
                    } else
                        string += (d10 + "d10");
                    text.add(Integer.toString(d10));
                }
                else {
                    string += (" - " + (-1) * d10 + "d10");
                    text.add("-");
                    text.add(Integer.toString((-1) * d10));
                }
                text.add("d10");
            }
            int d12 = rolls[i].getD12();
            if (d12 != 0) {
                selectedDice_[4] = 0;
                if (d12 > 0) {
                    if (!text.isEmpty()) {
                        string += (" + " + d12 + "d12");
                        text.add("+");
                    } else
                        string += (d12 + "d12");
                    text.add(Integer.toString(d12));
                }
                else {
                    string += (" - " + (-1) * d12 + "d12");
                    text.add("-");
                    text.add(Integer.toString((-1) * d12));
                }
                text.add("d12");
            }
            int d20 = rolls[i].getD20();
            if (d20 != 0) {
                selectedDice_[5] = 0;
                if (d20 > 0) {
                    if (!text.isEmpty()) {
                        string += (" + " + d20 + "d20");
                        text.add("+");
                    } else
                        string += (d20 + "d20");
                    text.add(Integer.toString(d20));
                }
                else {
                    string += (" - " + (-1) * d20 + "d20");
                    text.add("-");
                    text.add(Integer.toString((-1) * d20));
                }
                text.add("d20");
            }
            int mod = rolls[i].getModifier();
            if (mod != 0) {
                if (mod > 0)
                    string += (" + " + mod);
                else
                    string += (" - " + (-1) * mod);
                modifier[i] = Integer.toString(mod);
            }
            else
                modifier[i] = "";
            selectedDice[i] = selectedDice_;
            display[i] = string;

            String[] mainText = new String[text.size()];
            for (int j = 0; j < text.size(); j++)
                mainText[j] = text.get(j);

            MainText.add(mainText);

            text = new ArrayList<>();
        }

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button UseBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UseBtn = itemView.findViewById(R.id.useBtn);
        }
    }

    /*
    Updates the RecyclerAdapter if the user entered a new search query
     */
    public void update(DBHandler db, String name) {
        Roll[] rolls;
        if (name.equals("")) //Query is void (all rolls are displayed)
            rolls = db.getAllRolls();
        else {
            rolls = new Roll[1];
            rolls[0] = db.findRoll(name);
        }
        if (rolls[0] != null) { //Query isn't void (the roll matching the query is displayed)
            names = new String[rolls.length];
            selectedDice = new int[rolls.length][6];
            display = new String[rolls.length];
            MainText = new ArrayList<>();
            modifier = new String[rolls.length];
            initData(rolls);
            notifyDataSetChanged();
        }
        db.close();
    }

    public int[] getRightSelectedDice() {
        return rightSelectedDice;
    }

    public String[] getSelectedMainText() {
        return SelectedMainText;
    }

    public String getSelectedModifier() {
        return SelectedModifier;
    }

    public String getSelectedDisplay() {
        return SelectedDisplay;
    }
}