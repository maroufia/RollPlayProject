package com.example.rollplay;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHandler extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "rollsDB.db";
    public static final String TABLE_SAVED = "saved_rolls";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_D4 = "d4";
    public static final String COLUMN_D6 = "d6";
    public static final String COLUMN_D8 = "d8";
    public static final String COLUMN_D10 = "d10";
    public static final String COLUMN_D12 = "d12";
    public static final String COLUMN_D20 = "d20";
    public static final String COLUMN_MOD = "modifier";

    public DBHandler(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DB_NAME, factory, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SAVED_TABLE = "CREATE TABLE " + TABLE_SAVED + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_D4 + " INTEGER," +
                COLUMN_D6 + " INTEGER," +
                COLUMN_D8 + " INTEGER," +
                COLUMN_D10 + " INTEGER," +
                COLUMN_D12 + " INTEGER," +
                COLUMN_D20 + " INTEGER," +
                COLUMN_MOD + " INTEGER" + ")";
        db.execSQL(CREATE_SAVED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SAVED);
        onCreate(db);
    }

    public void addRoll (Roll R) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, R.getName());
        values.put(COLUMN_D4, R.getD4());
        values.put(COLUMN_D6, R.getD6());
        values.put(COLUMN_D8, R.getD8());
        values.put(COLUMN_D10, R.getD10());
        values.put(COLUMN_D12, R.getD12());
        values.put(COLUMN_D20, R.getD20());
        values.put(COLUMN_MOD, R.getModifier());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_SAVED, null, values);
        db.close();
    }

    public void removeRoll (String name) {
        Roll R = findRoll(name);
        if (R != null) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE_SAVED, COLUMN_NAME + "='" + name + "'", null);
            db.close();
        }

    }

    public Roll findRoll(String roll_name) {
        roll_name = roll_name.toLowerCase();
        String query = "SELECT * FROM " + TABLE_SAVED + " WHERE " +
                COLUMN_NAME + " = '" + roll_name + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Roll R = new Roll();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            R.set_id(Integer.parseInt(cursor.getString(0)));
            R.setName(cursor.getString(1));
            R.setD4(Integer.parseInt(cursor.getString(2)));
            R.setD6(Integer.parseInt(cursor.getString(3)));
            R.setD8(Integer.parseInt(cursor.getString(4)));
            R.setD10(Integer.parseInt(cursor.getString(5)));
            R.setD12(Integer.parseInt(cursor.getString(6)));
            R.setD20(Integer.parseInt(cursor.getString(7)));
            R.setModifier(Integer.parseInt(cursor.getString(8)));
            cursor.close();
        } else {
            R = null;
        }
        db.close();
        return R;
    }

    public Roll[] getAllRolls() {
        String query = "SELECT * FROM " + TABLE_SAVED;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Roll[] rolls = new Roll[cursor.getCount()];
        int i = 0;
        while (cursor.moveToNext()) {
            Roll R = new Roll();
            R.set_id(Integer.parseInt(cursor.getString(0)));
            R.setName(cursor.getString(1));
            R.setD4(Integer.parseInt(cursor.getString(2)));
            R.setD6(Integer.parseInt(cursor.getString(3)));
            R.setD8(Integer.parseInt(cursor.getString(4)));
            R.setD10(Integer.parseInt(cursor.getString(5)));
            R.setD12(Integer.parseInt(cursor.getString(6)));
            R.setD20(Integer.parseInt(cursor.getString(7)));
            R.setModifier(Integer.parseInt(cursor.getString(8)));
            rolls[i] = R;
            i++;
        }
        cursor.close();
        return rolls;
    }
}
