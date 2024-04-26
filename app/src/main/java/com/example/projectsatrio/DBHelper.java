package com.example.projectsatrio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "dollar.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_MATAUANG = "matauang";
    public static final String COLUMN_IDUANG = "id";
    public static final String COLUMN_MATAUANG = "matauang";
    public static final String COLUMN_KETERANGAN = "keterangan";
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_USERNAME + " TEXT," +
                    COLUMN_EMAIL + " TEXT," +
                    COLUMN_PASSWORD + " TEXT" + ")";

    private static final String CREATE_TOPUP_TABLE =
            "CREATE TABLE " + TABLE_MATAUANG + "(" +
                    COLUMN_IDUANG + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_MATAUANG + " TEXT," +
                    COLUMN_KETERANGAN + " TEXT" + ")";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_TOPUP_TABLE);
        Log.d("DBHelper", "Tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATAUANG);
        onCreate(db);
        Log.d("DBHelper", "Database upgraded successfully");
    }

    public void insertData(String mataUang, String keterangan) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MATAUANG, mataUang);
        values.put(COLUMN_KETERANGAN, keterangan);
        long result = db.insert(TABLE_MATAUANG, null, values);
        if (result == -1) {
            Log.e("DBHelper", "Error inserting data into matauang table");
        } else {
            Log.d("DBHelper", "Data inserted successfully into matauang table");
        }
        db.close();
    }
    public void editTopup(int topupId, String newMataUang, String newKeterangan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MATAUANG, newMataUang);
        values.put(COLUMN_KETERANGAN, newKeterangan);

        int updatedRows = db.update(TABLE_MATAUANG, values, COLUMN_IDUANG + "=?", new String[]{String.valueOf(topupId)});
        db.close();

        if (updatedRows > 0) {
            Log.d("DBHelper", "Topup with ID " + topupId + " updated successfully");
        } else {
            Log.e("DBHelper", "Error updating topup with ID " + topupId);
        }
    }

    public void deleteTopup(int topupId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int deletedRows = db.delete(TABLE_MATAUANG, COLUMN_IDUANG + "=?", new String[]{String.valueOf(topupId)});
        db.close();

        if (deletedRows > 0) {
            Log.d("DBHelper", "Topup with ID " + topupId + " deleted successfully");
        } else {
            Log.e("DBHelper", "Error deleting topup with ID " + topupId);
        }
    }


    public Cursor getAllTopupData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_IDUANG, COLUMN_MATAUANG, COLUMN_KETERANGAN};
        return db.query(TABLE_MATAUANG, columns, null, null, null, null, null);
    }
}
