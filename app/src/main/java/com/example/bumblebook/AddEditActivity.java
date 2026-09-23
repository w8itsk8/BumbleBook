package com.example.bumblebook;
// created by Kate Wheeler 21 Sept 2026
// BumbleBook Book Inventory Application
// AddEditActivity creates screen to add or edit a book in the database

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddEditActivity extends AppCompatActivity {

    // value to confirm whether
    private static final int NO_BOOK_ID = -1;

    // Initiate class variables
    DatabaseHelper db;
    int bookId;
    boolean isEditMode;

    // Initialize activity attributes
    TextView closeButton;
    EditText editTitle1;
    EditText editTitle2;
    EditText editTitle3;
    EditText editEdition;
    EditText editPrintYear;
    EditText editPublisher;
    EditText editAuthor;
    EditText editEditor;
    EditText editGenre;
    EditText editQuantity;
    Button saveButton;

    // Create activity
    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_addedit);

        db = new DatabaseHelper(this);

        // Check if activity was conjured with a selection
        bookId = getIntent().getIntExtra("book_id", NO_BOOK_ID);
        isEditMode = (bookId != NO_BOOK_ID);

        // Connect activity attribute to layout
        closeButton = findViewById(R.id.closeButton);
        editTitle1 = findViewById(R.id.editTitle1);
        editTitle2 = findViewById(R.id.editTitle2);
        editTitle3 = findViewById(R.id.editTitle3);
        editEdition = findViewById(R.id.editEdition);
        editPrintYear = findViewById(R.id.editPrintYear);
        editPublisher = findViewById(R.id.editPublisher);
        editAuthor = findViewById(R.id.editAuthor);
        editEditor = findViewById(R.id.editEditor);
        editGenre = findViewById(R.id.editGenre);
        editQuantity = findViewById(R.id.editQuantity);
        saveButton = findViewById(R.id.saveButton);

        // if activity was called with a selection...
        if (isEditMode) {

            // prepopulate selection details in text fields
            populateFieldsForEdit();

        }

        // Event listener for close
        closeButton.setOnClickListener(view -> finish());

        // Event listener for save
        saveButton.setOnClickListener(view -> saveBook());

    }

    // Function to pre-fill selected record in add/edit page
    private void populateFieldsForEdit() {

        // Summon selected book info
        Book book = db.getBookByID(bookId);

        // Ensure that book is there
        if (book == null) {

            // Print reason for error in log
            Log.e("AddEditActivity", "Selected book not found. ID provided: " + bookId);
            // Let user know in popup
            Toast.makeText(this, "There's been an internal error. Can't find selected book data.", Toast.LENGTH_SHORT).show();
            finish();
            return;

        }

        // Populate the text fields of add.edit screen with selected book info
        editTitle1.setText(book.getTitle1());
        editTitle2.setText(book.getTitle2());
        editTitle3.setText(book.getTitle3());
        editEdition.setText(book.getEdition());
        editPrintYear.setText(String.valueOf(book.getPrintYear()));
        editPublisher.setText(book.getPublisher());
        editAuthor.setText(book.getAuthor());
        editEditor.setText(book.getEditor());
        editGenre.setText(book.getGenre());
        editQuantity.setText(String.valueOf(book.getQuantity()));

    }

    // function to save book to the database, ensure fields contain valid data
    private void saveBook () {

        // convert and save user input from text fields
        String title1 = editTitle1.getText().toString().trim();
        String title2 = editTitle2.getText().toString().trim();
        String title3 = editTitle3.getText().toString().trim();
        String edition = editEdition.getText().toString().trim();
        String printYearText = editPrintYear.getText().toString().trim();
        String publisher = editPublisher.getText().toString().trim();
        String author = editAuthor.getText().toString().trim();
        String editor = editEditor.getText().toString().trim();
        String genre = editGenre.getText().toString().trim();
        String quantityText = editQuantity.getText().toString().trim();

        // make sure all the required fields have content
        if (title1.isEmpty() || printYearText.isEmpty() || publisher.isEmpty()
            || genre.isEmpty() || quantityText.isEmpty()) {

            // if one of the required fields is empty,
            // make a pop-up letting the user know something is missing
            Toast.makeText(this, "Make sure all required fields are filled in.", Toast.LENGTH_SHORT).show();
            // cancel out of function
            return;
        }

        int printYear;
        int quantity;

        // make sure print year and quanity are numerical
        try {

            // map values of printYear and quantity to integers
            printYear = Integer.parseInt(printYearText);
            quantity = Integer.parseInt(quantityText);

        } catch (NumberFormatException e) {

            // if values unable to be mapped to int
            // make a pop-up letting user know to make print year and quantity numbers
            Toast.makeText(this, "The year of printing and quanity must be numerical.", Toast.LENGTH_SHORT).show();
            // cancel out of function
            return;

        }

        // Build book object from user input
        Book book = new Book(
                isEditMode ? bookId : 0,
                title1, title2, title3, edition,
                printYear, publisher, author, editor,
                genre, quantity
        );

        // If we're updating values of an existing book
        if (isEditMode) {

            // update that book in the database
            db.updateBook(book);

        }
        // otherwise...
        else {

            // insert new book into database
            db.insertBook(book);

        }

        finish();

    }

}
