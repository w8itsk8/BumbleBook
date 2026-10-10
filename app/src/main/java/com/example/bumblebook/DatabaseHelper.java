package com.example.bumblebook;
// created by Kate Wheeler 20 Sept 2026
// BumbleBook Book Inventory Application
// DatabaseHelper manages interactions between frontend and database

// import necessary libraries
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

// initiate class
public class DatabaseHelper extends SQLiteOpenHelper {

    // Initialize class-level variables
    private static final String DATABASE_NAME = "inventoryapp.db";
    private static final int DATABASE_VERSION = 5;

    // Table names
    public static final String TABLE_BOOKS = "book_data";
    public static final String TABLE_LOGIN = "user_login_data";
    public static final String TABLE_GENRES = "genres";

    // Column titles for user information database
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD_HASH = "password_hash";
    public static final String COL_SALT = "salt";

    // Column titles for book database
    public static final String COL_TITLE1 = "title1";
    public static final String COL_TITLE2 = "title2";
    public static final String COL_TITLE3 = "title3";
    public static final String COL_EDITION = "edition";
    public static final String COL_PRINT_YEAR = "printYear";
    public static final String COL_PUBLISHER = "publisher";
    public static final String COL_AUTHOR = "author";
    public static final String COL_EDITOR = "editor";
    public static final String COL_GENRE = "genre";
    public static final String COL_QUANTITY = "quantity";

    // Column titles for genre database
    public static final String GENRE_ID = "book_id";
    public static final String GENRE_NAME = "genre_name";

    // Constructor
    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    // Define specifics of database tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // Catch errors creating user info table
        try {

            // Create SQL statement to initialize log-in data table
            db.execSQL("CREATE TABLE " + TABLE_LOGIN + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_USERNAME + " TEXT NOT NULL UNIQUE, " +
                    COL_PASSWORD_HASH + " TEXT NOT NULL, " +
                    COL_SALT + " TEXT NOT NULL" +
                    ")");

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to create table: " + e.getMessage());

        }

        // Catch errors creating Book info table
        try {

            // Create SQL statement to initialize book data table
            db.execSQL("CREATE TABLE " + TABLE_BOOKS + " (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_TITLE1 + " TEXT NOT NULL, " +
                    COL_TITLE2 + " TEXT, " +
                    COL_TITLE3 + " TEXT, " +
                    COL_EDITION + " TEXT, " +
                    COL_PRINT_YEAR + " INTEGER NOT NULL, " +
                    COL_PUBLISHER + " TEXT NOT NULL, " +
                    COL_AUTHOR + " TEXT, " +
                    COL_EDITOR + " TEXT, " +
                    COL_QUANTITY + " INTEGER NOT NULL DEFAULT 1" +
                    ")");

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to create table: " + e.getMessage());

        }

        // Catch errors creating Genre table
        try {

            // Create SQL statement to initialize genre table
            db.execSQL("CREATE TABLE " + TABLE_GENRES + " (" +
                    GENRE_ID + " INTEGER NOT NULL, " +
                    GENRE_NAME + " TEXT NOT NULL, " +
                    "FOREIGN KEY (" + GENRE_ID + ") REFERENCES " + TABLE_BOOKS + "(_id) ON DELETE CASCADE" +
                    ")");

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to create table: " + e.getMessage());

        }

        // Create sample accounts
        insertUser(db, "kate", "pa$$w0rd");
        insertUser(db, "user", "@Passw0rd");


    }

    // Function to run necessaries before onCreate or onUpgrade us called
    @Override
    public void onConfigure(SQLiteDatabase db) {

        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);

    }

    // Function to reset table if change has been made
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GENRES);
        onCreate(db);

    }

    // Function to view books in collection
    public Cursor readBooks() {

        // Catch errors calling function getReadableDatabase()
        try {

            // Load existing table
            SQLiteDatabase db = this.getReadableDatabase();

            // Create/Return query to return book data
            return db.query(
                    TABLE_BOOKS,
                    null,
                    null,
                    null,
                    null,
                    null,
                    COL_TITLE1 + " ASC"
            );

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to read books: " + e.getMessage());
            return null;

        }

    }

    // Function to ensure username and database both exist in log-in data table
    public boolean authenticate(String username, String password) {

        // Make sure neither username nor password is null
        if (username == null || password == null) {

            // Log error
            Log.e("DatabaseHelper", "Username or password was null");
            // Stop authentication process
            return false;

        }

        Cursor cursor = null;

        // Catch errors calling function getReadableDatabase()
        try {

            // Load existing table
            SQLiteDatabase db = this.getReadableDatabase();
            // Create query to check if username and password exist as a key-value pair in log-in data table
            cursor = db.query(
                    TABLE_LOGIN,
                    new String[]{COL_PASSWORD_HASH, COL_SALT},
                    COL_USERNAME + " = ?",
                    new String[]{username},
                    null,
                    null,
                    null
            );

            // if no account with specified username
            if(!cursor.moveToFirst()) {
                return false;
            }

            // fetch hash and salt
            String pwhash = cursor.getString(cursor.getColumnIndexOrThrow(COL_PASSWORD_HASH));
            String salt = cursor.getString(cursor.getColumnIndexOrThrow(COL_SALT));

            // verify encrypted password
            return PasswordHasher.verify(password, salt, pwhash);

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Authentication query failed: " + e.getMessage());
            return false;

        } finally {

            // If there's a problem returning the database...
            if (cursor != null) {

                // don't validate the user
                cursor.close();

            }

        }

    }


    // Retrieve book by ID
    public Book getBookByID(int id) {

        Cursor cursor = null;

        // Catch errors calling getReadableDatabase()
        try {

            // Load table
            SQLiteDatabase db = this.getReadableDatabase();

            // Query database for row with provided ID
            cursor = db.query(
                    TABLE_BOOKS,
                    null,
                    "_id = ?",
                    new String[]{String.valueOf(id)},
                    null,
                    null,
                    null
            );

            // if now row found with provided ID
            if (!cursor.moveToFirst()) {

                // Log error
                Log.e("DatabaseHelper", "No book found with ID " + id);
                // Cancel out of function
                return null;

            }

            // Return matching book
            return new Book(
                    cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE1)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE2)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_TITLE3)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EDITION)),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_PRINT_YEAR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_PUBLISHER)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_AUTHOR)),
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_EDITOR)),
                    getGenresForBook(id),
                    cursor.getInt(cursor.getColumnIndexOrThrow(COL_QUANTITY))
            );

        } catch (SQLiteException e) {

            // if attempt to fetch matching book fails
            Log.e("DatabaseHelper", "Failed to load book with ID " + e.getMessage());
            // Cancel out of function
            return null;

        } finally {

            // If the book with the matching ID was fetched but it returned null
            if (cursor != null) {

                // Close attempt
                cursor.close();

            }

        }

    }

    // Function to retrieve all the genres associated with a particular book
    public List<String> getGenresForBook(int bookId) {

        List<String> genres = new ArrayList<>();
        Cursor cursor = null;

        // Catch errors fetching genres
        try {

            // load database
            SQLiteDatabase db = this.getReadableDatabase();

            cursor = db.query(
                    TABLE_GENRES,
                    new String[]{GENRE_NAME},
                    GENRE_ID + " = ?",
                    new String[]{String.valueOf(bookId)},
                    null,
                    null,
                    null
            );

            while (cursor.moveToNext()) {
                genres.add(cursor.getString(cursor.getColumnIndexOrThrow(GENRE_NAME)));
            }

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to load genres for book " + bookId + ": " + e.getMessage());

        } finally {

            if (cursor != null) {

                cursor.close();

            }

        }

        return genres;

    }

    // insert new book into database
    public void insertBook(Book book) {

        // Catch errors writing to book database
        try {

            // load database
            SQLiteDatabase db = this.getWritableDatabase();

            // Create new object and add attributes
            ContentValues values = new ContentValues();
            values.put(COL_TITLE1, book.getTitle1());
            values.put(COL_TITLE2, book.getTitle2());
            values.put(COL_TITLE3, book.getTitle3());
            values.put(COL_EDITION, book.getEdition());
            values.put(COL_PRINT_YEAR, book.getPrintYear());
            values.put(COL_PUBLISHER, book.getPublisher());
            values.put(COL_AUTHOR, book.getAuthor());
            values.put(COL_EDITOR, book.getEditor());
            values.put(COL_QUANTITY, book.getQuantity());

            // insert object into database, capture ID
            long newBookId = db.insert(TABLE_BOOKS, null, values);

            // Insert a row into genre table for each specified
            for (String genreName : book.getGenres()) {

                ContentValues genreValues = new ContentValues();
                genreValues.put(GENRE_ID, newBookId);
                genreValues.put(GENRE_NAME, genreName);

                db.insert(TABLE_GENRES, null, genreValues);

            }

        } catch (SQLiteException e) {

            // Log error in console
            Log.e("DatabaseHelper", "Book insert failed. " + e.getMessage());

        }

    }

    // function to update existing book
    public void updateBook(Book book) {

        // load database
        SQLiteDatabase db = this.getWritableDatabase();

        // Begin transaction so all methods work or none do
        db.beginTransaction();

        // check for errors in update
        try {

            // Create new object and add attributes
            ContentValues values = new ContentValues();
            values.put(COL_TITLE1, book.getTitle1());
            values.put(COL_TITLE2, book.getTitle2());
            values.put(COL_TITLE3, book.getTitle3());
            values.put(COL_EDITION, book.getEdition());
            values.put(COL_PRINT_YEAR, book.getPrintYear());
            values.put(COL_PUBLISHER, book.getPublisher());
            values.put(COL_AUTHOR, book.getAuthor());
            values.put(COL_EDITOR, book.getEditor());
            values.put(COL_QUANTITY, book.getQuantity());

            // update selected object in database
            db.update(
                    TABLE_BOOKS,
                    values,
                    "_id = ?",
                    new String[]{String.valueOf(book.getID())}
            );

            // delete all the genre rows for the book
            db.delete(
                    TABLE_GENRES,
                    GENRE_ID + " = ?",
                    new String[]{String.valueOf(book.getID())}
            );

            // re-insert current set of genres
            for (String genreName : book.getGenres()) {

                ContentValues genreValues = new ContentValues();
                genreValues.put(GENRE_ID, book.getID());
                genreValues.put(GENRE_NAME, genreName);

                db.insert(TABLE_GENRES, null, genreValues);

            }

            // Now that the rest succeeded, mark transaction as successful
            db.setTransactionSuccessful();

        } catch (SQLiteException e) {

            // Log error in console
            Log.e("DatabaseHelper", "Book update failed. " + e.getMessage());

        } finally {

            // End the transaction regardless of outcome
            db.endTransaction();

        }

    }

    // Delete a book from the database
    public void deleteBook(int id) {

        // Make sure deletion doesn't throw any errors
        try {

            // load database into temp object
            SQLiteDatabase db = this.getWritableDatabase();

            // delete book from database
            db.delete(
                    TABLE_BOOKS,
                    "_id = ?",
                    new String[]{String.valueOf(id)}
            );

        } catch (SQLiteException e) {

            // if there was an error, log it to console
            Log.e("DatabaseHelper", "Failed to delete the book. " + e.getMessage());

        }

    }

    // Insert new user into database
    private boolean insertUser(SQLiteDatabase db, String username, String password) {

        // generate encrypted version of password
        String salt = PasswordHasher.generateSalt();
        String hash = PasswordHasher.hash(password, salt);

        // if hashing password failed...
        if (hash == null) {

            // log an error and return
            Log.e("DatabaseHelper", "Could not encrypt password for the username " + username);
            return false;

        }

        // package values
        ContentValues values = new ContentValues();
        values.put(COL_USERNAME, username);
        values.put(COL_PASSWORD_HASH, hash);
        values.put(COL_SALT, salt);

        // attempt insert, if insert fails return -1
        long insertRow = db.insert(TABLE_LOGIN, null, values);
        return insertRow != -1;

    }

    // register new user
    public boolean registerUser(String username, String password) {

        // check if either field entered is empty
        if (username == null || password == null) {

            // log an error
            Log.e("DatabaseHelper", "Username or password was null.");
            return false;

        }

        // catch errors inserting into database
        try {

            // fetch database and add to it
            SQLiteDatabase db = this.getWritableDatabase();
            return insertUser(db, username, password);

        } catch (SQLiteException e) {

            // log error generated
            Log.e("DatabaseHelper", "User registration failed." + e.getMessage());
            return false;

        }

    }



}
