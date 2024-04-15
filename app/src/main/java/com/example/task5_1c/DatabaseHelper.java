package com.example.task5_1c;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Info
    private static final String DATABASE_NAME = "myAppDatabase.db";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_USERS = "users";

    // User Table Columns
    private static final String KEY_USER_ID = "id";
    private static final String KEY_USER_USERNAME = "username";
    private static final String KEY_USER_PASSWORD = "password";
    private static final String KEY_USER_PLAYLIST = "playlist";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS +
                "(" +
                KEY_USER_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_USER_USERNAME + " TEXT UNIQUE," + // Define a unique key
                KEY_USER_PASSWORD + " TEXT," +
                KEY_USER_PLAYLIST + " TEXT" +
                ")";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }
    }

    // Insert a user into the database
    public void addUser(User user) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_USERNAME, user.getUsername());
            values.put(KEY_USER_PASSWORD, user.getPassword());
            values.put(KEY_USER_PLAYLIST, TextUtils.join(",", user.getPlaylist()));

            // Insert the user
            db.insertOrThrow(TABLE_USERS, null, values);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    // Update user's playlist
    public void updateUserPlaylist(String username, List<String> playlist) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_USER_PLAYLIST, TextUtils.join(",", playlist));

            // Updating playlist for user with that username
            db.update(TABLE_USERS, values, KEY_USER_USERNAME + " = ?", new String[]{username});
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    // Get user by username
    public User getUserByUsername(String username) {
        User user = null;
        String USERS_SELECT_QUERY =
                String.format("SELECT * FROM %s WHERE %s = ?",
                        TABLE_USERS, KEY_USER_USERNAME);

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(USERS_SELECT_QUERY, new String[]{String.valueOf(username)});
        try {
            if (cursor.moveToFirst()) {
                @SuppressLint("Range") String userUsername = cursor.getString(cursor.getColumnIndex(KEY_USER_USERNAME));
                @SuppressLint("Range") String userPassword = cursor.getString(cursor.getColumnIndex(KEY_USER_PASSWORD));
                @SuppressLint("Range") List<String> playlist = new ArrayList<>(Arrays.asList(cursor.getString(cursor.getColumnIndex(KEY_USER_PLAYLIST)).split(",")));
                user = new User(userUsername, userPassword, playlist);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return user;
    }
}