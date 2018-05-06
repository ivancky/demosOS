package com.osram.os.demos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ivan on 14/12/2017.
 */

public class Bluetooth_DBHandling {

    // define variables - db name, table name, version
    private static final String DATABASE_NAME = "db_bluetooth";
    private static final String TABLE_NAME = "tbl_bluetooth";
    private static final int DATABASE_VERSION = 1;

    // define table columns
    private final static String NAME_COLUMN = "name";
    private final static String ADD_COLUMN = "address";
    private final static String ID_COLUMN = "id";

    // declare object of SQLiteDatabase, Context, Cursor
    private final Context OUR_CONTEXT;
    private SQLiteDatabase sqLiteDatabase;
    private Cursor cursor;
    private DB_Helper db_helper;

    // constructor for Context
    public Bluetooth_DBHandling(Context our_context) {
        OUR_CONTEXT = our_context;
    }

    // define an array to hold column names
    String[] columns = new String[]{ID_COLUMN, NAME_COLUMN, ADD_COLUMN};

    // openDB() - connection to the DB
    public void openDB() {
        db_helper = new DB_Helper(OUR_CONTEXT);

        // allow read and write
        sqLiteDatabase = db_helper.getWritableDatabase();
    }

    // close DB()
    public void closeDB() {
        db_helper.close();
    }

    // insertData()
    public void insertData(String nameValue, String telValue) {
        // 1. set up object ContentValues
        ContentValues contentValues = new ContentValues();
        // 2. insert data into contentValues object - key + value
        contentValues.put(NAME_COLUMN, nameValue);
        contentValues.put(ADD_COLUMN, telValue);
        // 3. execute sql for insert
        // INSERT INTO tb_phonebook (nameValue, telValue)
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    // getAllData()
    public String getAllData() {
        // 1. set up Cursor object to point to first record in table
        // SELECT * FROM tb_phonebook
        cursor = sqLiteDatabase.query(TABLE_NAME, columns, null, null, null, null, null);
        // 2. define variable to store result
        String result = "";
        // 3. start read record in table - can also use array method
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            result += cursor.getInt(0) + "\t\t"
                    + cursor.getString(1) + "\t\t"
                    + cursor.getInt(2) + "\n";
        }
        return result;
    }

    // getName()
    public String getName(int contactId) {
        // 1. define a variable to store name
        String name_result = "";
        // 2. query SELECT * FROM tb_phonebook WHERE id = contactID
        cursor = sqLiteDatabase.query(TABLE_NAME, columns, ID_COLUMN + "=" + contactId, null, null, null, null);
        // 3. start read data from column name
//        if (cursor != null) {
//            cursor.moveToFirst();
//            name_result = cursor.getString(1);
//        }
        if (cursor != null && cursor.moveToFirst()){
            // do the work
            name_result = cursor.getString(1);
        }else{
            name_result = "";
        }
        return name_result;
    }

    // getTel()
    public String getAddress(int contactId) {
        // 1. define a variable to store name
        String add_result = "";
        // 2. query SELECT * FROM tb_phonebook WHERE id = contactID
        cursor = sqLiteDatabase.query(TABLE_NAME, columns, ID_COLUMN + "=" + contactId, null, null, null, null);
        // 3. start read data from column name
//        if (cursor != null) {
//            cursor.moveToFirst();
//            add_result = cursor.getString(2);
//        }
        if (cursor != null && cursor.moveToFirst()){
            // do the work
            add_result = cursor.getString(2);
        }else{
            add_result = "";
        }
        return add_result;
    }

    // updateContact()
    public void updateContact(int contactId, String contactName, String contactTelNo) {
        ContentValues cv = new ContentValues();

        cv.put(NAME_COLUMN, contactName);
        cv.put(ADD_COLUMN, contactTelNo);

        sqLiteDatabase.update(TABLE_NAME, cv, ID_COLUMN + "=" + contactId, null);
    }

    // deleteContact()
    public void deleteContact(int contactID){
        sqLiteDatabase.delete(TABLE_NAME, ID_COLUMN + "=" + contactID, null);
    }

    // Create a class that extends SQLiteOpenHelper
    private static class DB_Helper extends SQLiteOpenHelper {

        // constructor - creates the database
        public DB_Helper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        // creates table in database
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            // Create table tbl_phonebook (id, name, tel no)
            String sql = "CREATE TABLE " + TABLE_NAME
                    + " (" + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME_COLUMN + " TEXT NOT NULL, " + ADD_COLUMN + " INTEGER NOT NULL);";
            sqLiteDatabase.execSQL(sql);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        }
    } // end of DBHelper

} // end of Class
