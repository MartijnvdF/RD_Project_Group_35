package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.myapplication.ui.books.Book;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataBaseActivity extends SQLiteOpenHelper{
    public static final String DBName = "Login.db";
    private static DataBaseActivity dataBaseActivity;
    public static final int DBVersion = 1;
    public static final String TABLE_NAME = "users";
    public static final String TABLE_NAME1 = "first";

    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";

    public static final String FULL_NAME = "fullname";
    public static final String STUDENT_NUMBER = "studentnumber";
    public static final String MAJOR = "major";
    public static final String YEAR_USER = "year";
    public static final String EMAIL = "email";
    public static final String ISBN = "isbn";
    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String VERSION = "version";
    public static final String YEAR_BOOK = "year";
    public static final String COURSE = "course";





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
                .append(YEAR_USER)
                .append(" TEXT, ")
                .append(EMAIL)
                .append(" TEXT)");

        StringBuilder sql1;
        sql1 = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_NAME1)
                .append("(")
                .append("row_id INTEGER PRIMARY KEY, ")
                .append(ISBN)
                .append(" TEXT, ")
                .append(AUTHOR)
                .append(" TEXT, ")
                .append(TITLE)
                .append(" TEXT , ")
                .append(VERSION)
                .append(" TEXT, ")
                .append(YEAR_BOOK)
                .append(" TEXT, ")
                .append(COURSE)
                .append(" TEXT)");



        database.execSQL(sql.toString());
        database.execSQL(sql1.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int i, int i1) {
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        onCreate(database);
    }

    public void fillBooksDatabase(InputStream inputStream, String year_user){
        SQLiteDatabase database = this.getWritableDatabase();
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line;
        database.beginTransaction();
        try {
            line = bufferedReader.readLine();
            while((line = bufferedReader.readLine())!=null){
                //split by ','
                String[] columns = line.split(";");
                if(columns.length != 6){
                    Log.d("CSVParser", "skipping lines");
                    continue;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put(ISBN, columns[0].trim());
                contentValues.put(AUTHOR, columns[1].trim());
                contentValues.put(TITLE, columns[2].trim());
                contentValues.put(VERSION, columns[3].trim());
                contentValues.put(YEAR_BOOK, columns[4].trim());
                contentValues.put(COURSE, columns[5].trim());
                database.insert(year_user, null, contentValues);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        database.setTransactionSuccessful();
        database.endTransaction();
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public  void addUserToDatabase(){

    }

    public ArrayList<String> getBooksData(String table){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<String> courses = new ArrayList<>();

        try{
            Cursor cursor = database.rawQuery("SELECT DISTINCT course FROM " + table + " ORDER BY course", null);
            while (cursor.moveToNext()){
                courses.add(cursor.getString(0));
            }
            cursor.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courses;
    }

    public ArrayList<String> getUserData(String name){
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + USERNAME + " =?", new String[]{name});
        ArrayList<String> userData = new ArrayList<>();

        if(cursor.moveToFirst()){
            for(int i=0;i<7;i++){
                userData.add(cursor.getString(i));
            }
        }
        cursor.close();
        return userData;
    }

    public ArrayList<Book> getBookInfo(String course, String table){
        SQLiteDatabase database = this.getReadableDatabase();
        ArrayList<Book> books = new ArrayList<>();

        try {
            Cursor cursor = database.rawQuery("SELECT * FROM " + table + " WHERE " + COURSE + " =?", new String[]{course});
            while(cursor.moveToNext()){
                Book book = new Book(cursor.getString(1), cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5));
                books.add(book);
            }
            cursor.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        return books;
    }

    public String getYearUser(String user){
        SQLiteDatabase database = this.getReadableDatabase();
        String year_user = "";
        try {
            Cursor cursor = database.rawQuery("SELECT " + YEAR_USER + " FROM " + TABLE_NAME + " WHERE " + USERNAME + " =?", new String[]{user});
            cursor.moveToFirst();
            year_user = cursor.getString(0);
            cursor.close();
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return year_user;
    }

    public void updateData(String username, String name, String value){
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues contentValues= new ContentValues();
        contentValues.put(name, value);

        database.update(TABLE_NAME, contentValues, USERNAME + " ='" + username + "'", null);
    }

    public void insertBook(String isbn, String author, String title, String version, String year, String course, String table){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("isbn", isbn);
        contentValues.put("author", author);
        contentValues.put("title", title);
        contentValues.put("version", version);
        contentValues.put("year", year);
        contentValues.put("course", course);
        database.insert(table, null, contentValues);
    }

    public void deleteUser(String username){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(TABLE_NAME, USERNAME + " ='" + username + "'", null);
    }

    public Boolean insertUser(String username, String password, String fullname, String email, String major, String year){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("fullname", fullname);
        contentValues.put("studentnumber", "");
        contentValues.put("major", major);
        contentValues.put("year", year);
        contentValues.put("email", email);

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

    public Boolean checkISBN(String isbn, String title){
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM " + TABLE_NAME1 + " WHERE " + ISBN + " = ? AND " + TITLE + " = ?", new String[]{isbn, title});
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

    public Boolean isBooksEmpty(String table){
        SQLiteDatabase database = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(database, table) == 0;

    }
}
