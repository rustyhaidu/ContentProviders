package com.contentprovidersnew;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE = "MyDatabase";
    private static final int DATABASE_VERSION = 1;

    static final String TABLE_VICS = "vics";
    static final String COLUMN_ID = "_ID";
    static final String COLUMN_NAME = "name";

    private SQLiteDatabase database;

    DatabaseHelper(Context context) {
        super(context, DATABASE, null, DATABASE_VERSION);
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL = " CREATE TABLE " + TABLE_VICS + " ( " +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL);";
        db.execSQL(SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

    void addContact(String name) {
        String SQL = "INSERT INTO " + TABLE_VICS + "(" + COLUMN_NAME + ") VALUES (\"" + name + "\")";
        database.execSQL(SQL, new String[]{});
    }

    Row[] getContacts() {
        String SQL = "SELECT " + COLUMN_ID + ", " + COLUMN_NAME + " FROM " + TABLE_VICS;
        Cursor cursor = database.rawQuery(SQL, new String[]{});

        int ct = cursor.getCount();
        Row[] rows = new Row[ct];
        if (ct > 0) {
            cursor.moveToFirst();
            for (int i = 0; i < ct; i++) {
                String id = cursor.getString(cursor.getColumnIndex(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
                rows[i] = new Row(id, name);
                cursor.moveToNext();
            }
        }
        cursor.close();
        return rows;
    }
}

