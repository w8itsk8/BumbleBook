package com.example.bumblebook;
// created by Kate Wheeler 10 Oct 2026
// BumbleBook Book Inventory Application
// NewUserActivity creates the app's screen for creating a new user

// import libraries
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.DatabaseMetaData;

// Instantiate c;ass
public class NewUserActivity extends AppCompatActivity {

    // Initialize class-level variables
    DatabaseHelper db;
    EditText newusername;
    EditText newpassword;
    EditText confirmpassword;
    Button createbutton;

    // when activity started
    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_newuser);

        db = new DatabaseHelper(this);

        // connect to layout attributes
        newusername = findViewById(R.id.etNewUsername);
        newpassword = findViewById(R.id.etNewPassword);
        confirmpassword = findViewById(R.id.etConfirmPassword);
        createbutton = findViewById(R.id.createButton);

        // make sure all of the fields exist in the layout
        if(newusername == null || newpassword == null || confirmpassword == null || createbutton == null) {

            // log an error
            Log.e("NewUserActivity", "pme pf the key fields in the layout failed to render");
            return;

        }

        // When create account button is pressed
        createbutton.setOnClickListener(view -> createAccount());

    }

    // function to ensure username is unique, password fields match, and create new user
    private void createAccount() {

        // read user-entered fields
        String username = newusername.getText().toString().trim();
        String password = newpassword.getText().toString().trim();
        String confirmpw = confirmpassword.getText().toString().trim();

        // make sure no fields were left blank
        if (username.isEmpty() || password.isEmpty() || confirmpw.isEmpty()) {

            // prompt user to fill out all fields, then return to entry screen
            Toast.makeText(this, "Make sure to enter all fields.", Toast.LENGTH_SHORT).show();
            return;

        }

        // if password and password confirm fields don't match
        if (!password.equals(confirmpw)) {

            // let user know the problem then return to entry
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;

        }

        // if account creation successful
        if (db.registerUser(username, password)) {

            // Let user know creation was successful and return to login
            Toast.makeText(this, "Account create successful! Log in with your new credentials.", Toast.LENGTH_SHORT).show();
            finish();

        }
        // otherwise...
        else {

            // Display message letting user know user add failed
            Toast.makeText(this, "Unable to create account! Try again.", Toast.LENGTH_SHORT).show();

        }

    }

    // control what happens when activity ends
    @Override
    protected void onDestroy() {

        super.onDestroy();
        db.close();

    }

}
