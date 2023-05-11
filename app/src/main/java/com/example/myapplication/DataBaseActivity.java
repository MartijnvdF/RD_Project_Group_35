package com.example.myapplication;
import static java.sql.Types.NULL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseActivity extends SQLiteOpenHelper{
    public static final String DBName = "Login.db";
    private static DataBaseActivity dataBaseActivity;
    public static final int DBVersion = 1;
    public static final String TABLE_NAME = "users";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String FULL_NAME = "fullname";
    public static final String STUDENT_NUMBER = "studentnumber";
    public static final String MAJOR = "major";
    public static final String YEAR = "year";
    public static final String EMAIL = "email";





    public DataBaseActivity(Context context) {
        super(context, DBName, null, DBVersion);
    }

    public static DataBaseActivity instanceOfDatabase(Context context){
        if(dataBaseActivity == null)
            dataBaseActivity = new DataBaseActivity(context);
        return dataBaseActivity;
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        StringBuilder sql;
        sql = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME)
                .append("(")
                .append(USERNAME)
                .append(" TEXT PRIMARY KEY, ")
                .append(PASSWORD)
                .append(" TEXT, ")
                .append(FULL_NAME)
                .append(" TEXT, ")
                .append(STUDENT_NUMBER)
                .append(" TEXT, ")
                .append(MAJOR)
                .append(" TEXT, ")
                .append(YEAR)
                .append(" TEXT, ")
                .append(EMAIL)
                .append(" TEXT)");

        database.execSQL(sql.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public  void addUserToDatabase(){

    }

    public ArrayList<String> getData(String name){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " =?", new String[]{name});
        ArrayList<String> userData = new ArrayList<>();

        if(cursor.moveToFirst()){
            for(int i=0;i<7;i++){
                String str = cursor.getString(i);
                if (str.equals(""))
                    userData.add("Unkown");
                else
                    userData.add(str);
            }
        }
        return userData;
    }

    public void updateData(String username, String name, String value){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put(name, value);

        database.update(TABLE_NAME, contentValues, USERNAME + " ='" + username + "'", null);
    }

    public void deleteUser(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, USERNAME + " ='" + username + "'", null);
    }

    public Boolean insertUser(String username, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", "");
        contentValues.put("studentnumber", "");
        contentValues.put("major", "");
        contentValues.put("year", "");
        contentValues.put("email", "");

        long result = database.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Boolean checkUsername(String username) {
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = ?", new String[]{username});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " = ? AND " + PASSWORD + " = ?", new String[] {username,password});
        int count = cursor.getCount();
        cursor.close();
        return count > 0;
    }
}
