package com.company.arunas.passwordmanager.sql;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.company.arunas.passwordmanager.model.Password;
import com.company.arunas.passwordmanager.model.User;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"JavaDoc", "FieldCanBeLocal"})
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "UserManager.db";

    // User table name
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    // Password table name
    private static final String TABLE_PASSWORD = "password";

    //Password table columns names
    private static final String COLUMN_PASSWORD_ID = "password_id";
    private static final String COLUMN_PASSWORD_FOR = "password_for";
    private static final String COLUMN_PASSWORD_ADD = "password_add";
    private static final String COLUMN_USERNAME_ADD = "user_add";

    // Create user table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // Drop table if already exists sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;


    // Create password table sql query
    private String CREATE_PASSWORD_TABLE = "CREATE TABLE " + TABLE_PASSWORD + "("
            + COLUMN_PASSWORD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_PASSWORD_FOR + " TEXT,"
            + COLUMN_USERNAME_ADD + " TEXT," + COLUMN_PASSWORD_ADD + " TEXT" + ")";

    // Drop table if already exists sql query
    private String DROP_PASSWORD_TABLE = "DROP TABLE IF EXISTS " + TABLE_PASSWORD;

    /**
     * Constructor
     *
     * @param context
     */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_PASSWORD_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_PASSWORD_TABLE);

        // Create tables again
        onCreate(db);

    }

    /**
     * This method is to create user record
     *
     * @param user from User class
     */
    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // Inserting Row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to create user record
     *
     * @param pass from Password class
     */
    public void addPassword(Password pass) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values1 = new ContentValues();
        values1.put(COLUMN_PASSWORD_FOR, pass.getLabelFor());
        values1.put(COLUMN_PASSWORD_ADD, pass.getLabelPassword());
        values1.put(COLUMN_USERNAME_ADD, pass.getLabelUsername());

        // Inserting Row
        db.insert(TABLE_PASSWORD, null, values1);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };
        // Sorting orders
        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Query the user table

        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                // Adding user record to list
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public ArrayList<Password> getAllPasswords() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_PASSWORD_FOR,
                COLUMN_PASSWORD_ADD,
                COLUMN_USERNAME_ADD
        };
        // Sorting orders
        String sortOrder =
                COLUMN_PASSWORD_FOR + " ASC";

        ArrayList<Password> passwordList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Query the user table

        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_PASSWORD, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

               // Integer passwordId = Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_ID)));
                String  passwordFor = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_FOR));
                String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD_ADD));
                String usernameItem = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME_ADD));

                //Set member variables
                Password passwordItem = new Password(passwordFor, password, usernameItem);


                // Adding user record to list
                passwordList.add(passwordItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return passwordList;
    }
    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // Array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // Selection argument
        String[] selectionArgs = {email};

        // Query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    /**
     * This method to check if account exist or not
     *
     * @param account
     * @return true/false
     */
    public boolean checkAccount(String account) {

        // Array of columns to fetch
        String[] columns = {
                COLUMN_PASSWORD_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // Selection criteria
        String selection = COLUMN_PASSWORD_FOR + " = ?";

        // Selection argument
        String[] selectionArgs = {account};

        // Query password table with condition
        /**
         * Here query function is used to fetch records from password table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT password_id FROM passwords WHERE password_for = 'Gmail Account';
         */
        Cursor cursor = db.query(TABLE_PASSWORD, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }
}