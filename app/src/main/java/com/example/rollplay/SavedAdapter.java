package com.example.rollplay;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SavedAdapter extends RecyclerView.Adapter<SavedAdapter.ViewHolder> {

    private final Button confirmBtn;
    private String[] names;

    private int[][] selectedDice;
    private int[] rightSelectedDice;
    private ArrayList<ArrayList<String>> MainText;
    private ArrayList<String> SelectedMainText;
    private String[] modifier;
    private String SelectedModifier;
    private String[] display;
    private String SelectedDisplay;

    public SavedAdapter(DBHandler db, Button confirmBtn) {
        this.confirmBtn = confirmBtn;
        Roll[] rolls = db.getAllRolls();
        names = new String[rolls.length];
        selectedDice = new int[rolls.length][6];
        display = new String[rolls.length];
        MainText = new ArrayList<>();
        modifier = new String[rolls.length];
        initData(rolls);
        db.close();
    }

    @NonNull
    @Override
    public SavedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saved_roll_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedAdapter.ViewHolder holder, int position) {
        String text = "Name: " + names[position] + "\nRoll: " + display[position];
        holder.UseBtn.setText(text);
        holder.UseBtn.setOnClickListener (v -> {
            SelectedMainText = MainText.get(holder.getAbsoluteAdapterPosition());
            SelectedModifier = modifier[holder.getAbsoluteAdapterPosition()];
            rightSelectedDice = selectedDice[holder.getAbsoluteAdapterPosition()];
            SelectedDisplay = display[holder.getAbsoluteAdapterPosition()];
            if (!SelectedModifier.equals(""))
                SelectedDisplay = SelectedDisplay.substring(0, SelectedDisplay.length()-4);
            confirmBtn.setEnabled(true);
        });
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    private void initData(Roll[] rolls) {
        for (int i=0; i < rolls.length; i++) {
            int[] selectedDice_ = {1, 1, 1, 1, 1, 1};
            names[i] = rolls[i].getName();
            String string = "";
            ArrayList<String> text = new ArrayList<>();
            int d4 = rolls[i].getD4();
            if (d4 != 0) {
                selectedDice_[0] = 0;
                if (d4 > 0) {
                    string += (d4 + "d4");
                    text.add(Integer.toString(d4));
                }
                else {
                    string += (" - " + d4 + "d4");
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
                    string += (" - " + d8 + "d8");
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
            MainText.add(text);
        }
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
        private final Button UseBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UseBtn = itemView.findViewById(R.id.useBtn);
        }
    }

    public void update(DBHandler db, String name) {
        Roll[] rolls;
        if (name.equals(""))
            rolls = db.getAllRolls();
        else {
            rolls = new Roll[1];
            rolls[0] = db.findRoll(name);
        }
        if (rolls[0] != null) {
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

    public ArrayList<String> getSelectedMainText() {
        return SelectedMainText;
    }

    public String getSelectedModifier() {
        return SelectedModifier;
    }

    public String getSelectedDisplay() {
        return SelectedDisplay;
    }
}
