package com.example.studentapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "posts.db";
    private static final int DATABASE_VERSION = 2;

    public static final String TABLE_POSTS = "posts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TEXT = "text";
    public static final String COLUMN_IMAGE_URI = "image_uri";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_LIKE_COUNT = "like_count";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_POSTS + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEXT + " TEXT, " +
                    COLUMN_IMAGE_URI + " TEXT, " +
                    COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                    COLUMN_LIKE_COUNT + " INTEGER DEFAULT 0" +
                    ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE " + TABLE_POSTS + " ADD COLUMN " + COLUMN_LIKE_COUNT + " INTEGER DEFAULT 0");
        }
    }
}
