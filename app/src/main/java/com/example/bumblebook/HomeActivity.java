package com.example.bumblebook;
// created by Kate Wheeler 20 Sept 2026
// BumbleBook Book Inventory Application
// HomeActivity creates app home screen which displays the main book database

// Import necessary libraries
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;

// Create class
public class HomeActivity extends AppCompatActivity {

    // Initialize class-level variables
    private static final int WIDTH_TITLE1 = 200;
    private static final int WIDTH_TITLE2 = 300;
    private static final int WIDTH_TITLE3 = 200;
    private static final int WIDTH_EDITION = 200;
    private static final int WIDTH_PRINT_YEAR = 150;
    private static final int WIDTH_PUBLISHER = 200;
    private static final int WIDTH_AUTHOR = 150;
    private static final int WIDTH_EDITOR = 150;
    private static final int WIDTH_GENRE = 250;
    private static final int WIDTH_QUANTITY = 100;

    //Initialize class-level variables
    DatabaseHelper db;
    LinearLayout table;
    Button addButton;
    Button editButton;
    Button deleteButton;
    private Book selectedBook = null;
    private LinearLayout selectedRow = null;


    // Function to create home screen activity
    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_home);

        // Call table for display
        db = new DatabaseHelper(this);
        table = findViewById(R.id.table);
        addButton = findViewById(R.id.addButton);
        editButton = findViewById(R.id.editButton);
        deleteButton = findViewById(R.id.deleteButton);

        // Make sure table is not null
        if (table == null) {
            Log.e("HomeActivity", "Table was null");
            return;
        }

        // Listener for presses of edit button
        editButton.setOnClickListener(view -> {

            // Make sure there's a book selected to edit
            if (selectedBook == null) {

                // Log error
                Log.e("HomeActivity", "No book was selected when edit button was pressed");
                return;

            }

            // Call Add/Edit activity
            Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
            intent.putExtra("book_id", selectedBook.getID());
            startActivity(intent);

        });

        // Listener for presses of add button
        addButton.setOnClickListener(view -> {

            // Call Add/Edit activity
            Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
            startActivity(intent);

        });

        // Listener for presses of delete button
        deleteButton.setOnClickListener(view -> {

            // if there's no selected book to delete
            if (selectedBook == null) {

                // Log problem in console
                Log.e("HomeActivity", "No book was selected when delete button was pressed");
                // cancel out of function
                return;

            }

            new AlertDialog.Builder(this)
                    .setTitle("Delete Book")
                    .setMessage("Delete \"" + selectedBook.getTitle1() + "\" by "
                        + selectedBook.getAuthor() + "? This will be permanent!")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        db.deleteBook(selectedBook.getID());
                        loadBooks();
                        updateButtonState();

                    })
                    .setNegativeButton("Cancel", null)
                    .show();

        });

    }

    @Override
    protected void onResume() {

        super.onResume();
        loadBooks();
        updateButtonState();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        db.close();

    }


    // Declare function that loads books into the table
    private void loadBooks() {

        table.removeAllViews();

        // Clear any old selections
        selectedRow = null;
        selectedBook = null;

        Cursor cursor = db.readBooks();

        // Make sure cursor isn't null
        if (cursor == null) {
            Log.e("HomeActivity", "Call to readBooks returned null");
            return;
        }

        // Ensure cursor.close runs successfully
        try {

            // Iterate through books in table
            if (cursor.moveToFirst()) {

                // Add the book to the table
                do {

                    // Ensure call to addBookRow doesn't error out
                    try {

                        addBookRow(cursor);

                    } catch (IllegalArgumentException e) {

                        // Log the problematic row
                        Log.e("HomeActivity", "Skipping malformed row: " + e.getMessage());
                    }
                }

                // While there are still books to add
                while (cursor.moveToNext());

            }

        } finally {

            // Close if error is thrown partway through the loop
            cursor.close();

        }

    }



    // Declare function to add a new row to the table
    private void addBookRow(Cursor cursor) {

        // Define row in table
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);

        // Create new book from cursor data
        Book book = new Book(
                cursor.getInt(cursor.getColumnIndexOrThrow("_id")),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE1)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE2)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_TITLE3)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EDITION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PRINT_YEAR)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PUBLISHER)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_AUTHOR)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_EDITOR)),
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_GENRE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_QUANTITY))
        );

        // Add content of each cell in row
        row.addView(newCell(book.getTitle1(), WIDTH_TITLE1));
        row.addView(newCell(book.getTitle2(), WIDTH_TITLE2));
        row.addView(newCell(book.getTitle3(), WIDTH_TITLE3));
        row.addView(newCell(book.getEdition(), WIDTH_EDITION));
        row.addView(newCell(String.valueOf(book.getPrintYear()), WIDTH_PRINT_YEAR));
        row.addView(newCell(book.getPublisher(), WIDTH_PUBLISHER));
        row.addView(newCell(book.getAuthor(), WIDTH_AUTHOR));
        row.addView(newCell(book.getEditor(), WIDTH_EDITOR));
        row.addView(newCell(book.getGenre(), WIDTH_GENRE));
        row.addView(newCell(String.valueOf(book.getQuantity()), WIDTH_QUANTITY));

        // Listen for tap of row
        row.setOnClickListener(v -> {

            // If same row tapped again
            if (row == selectedRow) {

                // Set temp object back to null
                row.setBackground(null);
                selectedRow = null;
                selectedBook = null;

            // Otherwise...
            } else {

                // Remove outline on currently selected row
                if (selectedRow != null) {
                    selectedRow.setBackground(null);
                }

                // Outline new row instead
                row.setBackgroundResource(R.drawable.selected_row);
                selectedRow = row;
                selectedBook = book;

            }

            updateButtonState();

        });

        // Add row content to table
        table.addView(row);

    }


    // Declare function to add a new cell to the row
    private TextView newCell(String text, int widthDp) {

        // Define cell
        TextView cell = new TextView(this);
        int widthPx = (int) (widthDp * getResources().getDisplayMetrics().density);
        cell.setLayoutParams(new LinearLayout.LayoutParams(widthPx, LinearLayout.LayoutParams.WRAP_CONTENT));

        // If there's content for the cell
        if (text != null) {

            // Assign the content to the cell
            cell.setText(text);

        }
        // Otherwise...
        else {

            // Assign an empty string to the cell
            cell.setText("");

        }

        // Set cell design and return the whole thing, text and padding
        cell.setPadding(15, 15, 15, 15);
        return cell;

    }

    // update button availability based on whether there is a current selection
    private void updateButtonState() {

        boolean hasSelection = (selectedBook != null);
        editButton.setEnabled(hasSelection);
        deleteButton.setEnabled(hasSelection);

    }


}
