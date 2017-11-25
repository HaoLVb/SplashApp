package com.example.levanhao.splashapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.levanhao.splashapp.model.LoginInfo;
import com.example.levanhao.splashapp.model.UserInformationModel;


/**
 * Created by vokhuyet on 27/07/2016.
 */
public class LoginHelper extends SQLiteOpenHelper {
    private final static int VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "userManager";
    // Contacts table name
    private static final String TABLE_USER = "user";
    private static final String TABLE_LOGIN = "login";
    // user Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "username";
    private static final String KEY_TOKEN = "token";
    private static final String AVATAR = "avatar";

    private static final String PHONE_NUMBER = "phone_number";
    private static final String PASSWORD = "password";

    public LoginHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USER + "("
                + KEY_ID + " integer PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_TOKEN + " TEXT," + AVATAR + " TEXT" + ")";
        sqLiteDatabase.execSQL(sql);


        String sql2 = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + PHONE_NUMBER + " TEXT PRIMARY KEY," + PASSWORD + " TEXT"
                + ")";
        sqLiteDatabase.execSQL(sql2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        /*sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(sqLiteDatabase);*/
    }

    public void addUser(UserInformationModel model) {
        if (getUser() == null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(KEY_ID, model.getId());
            values.put(KEY_NAME, model.getUserName());
            values.put(KEY_TOKEN, model.getToken());
            values.put(AVATAR, model.getAvatar());
            // Inserting Row
            db.insert(TABLE_USER, null, values);
            values.clear();
            db.close(); // Closing database connection
        }
    }

    public UserInformationModel getUser() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USER;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return new UserInformationModel(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
        }
        cursor.close();
        return null;
    }

    public void deleteUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public LoginInfo getLogin() {
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            return new LoginInfo(cursor.getString(0), cursor.getString(1));
        }
        cursor.close();
        return null;
    }

    public void addUser(LoginInfo model) {
        if (getLogin() == null) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(PHONE_NUMBER, model.getPhoneNumber());
            values.put(PASSWORD, model.getPassword());

            // Inserting Row
            db.insert(TABLE_LOGIN, null, values);
            values.clear();
            db.close(); // Closing database connection
        }
    }

    public void deleteLogin() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }


}
