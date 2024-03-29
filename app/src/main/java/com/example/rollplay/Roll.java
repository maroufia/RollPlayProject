package com.example.rollplay;

/*
Class that describes a roll
 */
public class Roll {

    private int _id; //Roll's id
    private String name; //Roll's name
    private int d4; //Roll's d4 value
    private int d6; //Roll's d6 value
    private int d8; //Roll's d8 value
    private int d10; //Roll's d10 value
    private int d12; //Roll's d12 value
    private int d20; //Roll's d20 value
    private int modifier; //Roll's id

    public Roll() {

    }

    public Roll(String name, int d4, int d6, int d8, int d10, int d12, int d20, int modifier) {
        this.name = name;
        this.d4 = d4;
        this.d6 = d6;
        this.d8 = d8;
        this.d10 = d10;
        this.d12 = d12;
        this.d20 = d20;
        this.modifier = modifier;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getD4() {
        return d4;
    }

    public void setD4(int d4) {
        this.d4 = d4;
    }

    public int getD6() {
        return d6;
    }

    public void setD6(int d6) {
        this.d6 = d6;
    }

    public int getD8() {
        return d8;
    }

    public void setD8(int d8) {
        this.d8 = d8;
    }

    public int getD10() {
        return d10;
    }

    public void setD10(int d10) {
        this.d10 = d10;
    }

    public int getD12() {
        return d12;
    }

    public void setD12(int d12) {
        this.d12 = d12;
    }

    public int getD20() {
        return d20;
    }

    public void setD20(int d20) {
        this.d20 = d20;
    }

    public int getModifier() {
        return modifier;
    }

    public void setModifier(int modifier) {
        this.modifier = modifier;
    }
}
