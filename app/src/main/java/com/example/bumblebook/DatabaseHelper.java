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

// initiate class
public class DatabaseHelper extends SQLiteOpenHelper {

    // Initialize class-level variables
    private static final String DATABASE_NAME = "inventoryapp.db";
    private static final int DATABASE_VERSION = 2;

    // Table names
    public static final String TABLE_BOOKS = "book_data";
    public static final String TABLE_LOGIN = "user_login_data";

    // Column titles for user information database
    public static final String COL_USERNAME = "username";
    public static final String COL_PASSWORD = "password";

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
                    COL_PASSWORD + " TEXT NOT NULL" +
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
                    COL_GENRE + " TEXT NOT NULL, " +
                    COL_QUANTITY + " INTEGER NOT NULL DEFAULT 1" +
                    ")");

        } catch (SQLiteException e) {

            // Log error
            Log.e("DatabaseHelper", "Failed to create table: " + e.getMessage());

        }



        // Create sample values in log-in data table
        db.execSQL("INSERT INTO user_login_data (username, password) VALUES ('kate', 'pa$$w0rd')");
        db.execSQL("INSERT INTO user_login_data (username, password) VALUES ('profdimarzio', 'Snhu123!')");
        /*
        // Create sample values in book data table
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('The Dead Sea Scrolls', 'A New Translation', null, 'Translated and with Commentary', 1996, 'HarperCollins', null, 'Michael Wise, Martin Abegg, Jr., & Edward Cook', 'Classics, Ancient Text, Translation', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('Code', 'The Hidden Language of Computer Hardware and Software', null, null, 2023, 'Pearson Education', 'Charles Petzold', null, 'Nonfiction, Computer Science', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('The Simon & Schuster Pocket Book of Chess', null, null, null, 1988, 'Simon & Schuster', 'Raymond Keene', null, 'Nonfiction, Guidebook, Chess, Games', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('Where''d You Go, Bernadette', null, null, null, 2012, 'Little, Brown and Company', 'Maria Semple', null, 'Fiction, Found, Realistic Fiction', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('All Quiet on the Western Front', null, null, 'Translated from the German by A. W. Wheen', 1928, 'Ballantine Books', 'Erich Maria Remarque', null, 'Fiction, Historical Fiction, War Novel, Foreign Literature, German Language', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('To Kill a Mockingbird', null, null, null, 2002, 'HarperCollins Perennial Classics', 'Harper Lee', null, 'Fiction, Historical Fiction, Social Justice, Classics', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('Hamnet', 'A Novel of the Plague', null, null, 2020, 'Penguin Random House', 'Maggie O''Farrell', null, 'Fiction, Historical Fiction', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('A History of the World in Twelve Shipwrecks', null, null, null, 2024, 'Weidenfeld & Nicolson', 'David Gibbins', null, 'Nonfiction, History, Maritime Disaster', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('David and Goliath', 'Underdogs, Misfits, and the Art of Battling Giants', null, 'With a New Afterword by the Author', 2013, 'Back Bay Books, Little, Brown and Company', 'Malcolm Gladwell', null, 'Nonfiction, History', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('Haunted Lancaster County Pennsylvania', 'Ghosts and Other Strange Occurrences', null, null, 1994, 'Science Press', 'Dorothy Burtz Fiedel', null, 'Nonfiction, Supernatural, Ghost, Local, History', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('The Guernsey Literary and Potato Peel Pie Society', null, null, null, 2009, 'Dial Press Trade', 'Mary Ann Shaffer and Annie Barrows', null, 'Fiction, Historical Fiction, World War II Novel, Found, Letters', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('The Nickel Boys', null, null, null, 2020, 'Anchor Books, Penguin Random House', 'Colson Whitehead', null, 'Fiction, Historical Fiction, Social Justice', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('Readings from Literature', null, null, null, 1915, 'American Book Company', null, 'Reuben Post Halleck and Elizabeth Graeme Barbour', 'Fiction, Collection, Short Story, Poetry, Anthology', 1)");
        db.execSQL("INSERT INTO book_data (title1, title2, title3, edition, printYear, publisher, author, editor, genre, quantity) VALUES ('The Book Thief', null, null, 'Anniversary Edition with New Context', 2005, 'Alfred A. Knopf, Random House Children''s Books', 'Markus Zusak', null, 'Fiction, Historical Fiction, World War II Novel', 1)");

         */

    }

    // Function to reset table if change has been made
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
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
                    new String[]{"_id"},
                    COL_USERNAME + " = ? AND " + COL_PASSWORD + " = ?",
                    new String[]{username, password},
                    null,
                    null,
                    null
            );

            return cursor.getCount() > 0;

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
                    cursor.getString(cursor.getColumnIndexOrThrow(COL_GENRE)),
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
            values.put(COL_GENRE, book.getGenre());
            values.put(COL_QUANTITY, book.getQuantity());

            // insert object into database
            db.insert(TABLE_BOOKS, null, values);

        } catch (SQLiteException e) {

            // Log error in console
            Log.e("DatabaseHelper", "Book insert failed. " + e.getMessage());

        }

    }

    // function to update existing book
    public void updateBook(Book book) {

        // check for errors in update
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
            values.put(COL_GENRE, book.getGenre());
            values.put(COL_QUANTITY, book.getQuantity());

            // update selected object in database
            db.update(
                    TABLE_BOOKS,
                    values,
                    "_id = ?",
                    new String[]{String.valueOf(book.getID())}
            );

        } catch (SQLiteException e) {

            // Log error in console
            Log.e("DatabaseHelper", "Book update failed. " + e.getMessage());

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

}
