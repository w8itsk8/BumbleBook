package com.example.bumblebook;
// created by Kate Wheeler 20 Sept 2026
// BumbleBook Book Inventory Application
// LogInActivity creates the app log-in screen

// Import necessary libraries
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

// Initialize main activity class
public class LogInActivity extends AppCompatActivity {

    // Initialize class-level variables
    DatabaseHelper db;
    Button login;
    EditText userUN;
    EditText userPW;

    // Function to manage log-in screen activity
    @Override
    protected void onCreate(Bundle state) {

        super.onCreate(state);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);

        login = findViewById(R.id.button);

        // Make sure button is not null
        if (login == null) {
            Log.e("LogInActivity", "LogIn button was null");
            return;
        }

        // Assign value entered by user in username field to variable
        userUN = findViewById(R.id.et1);

        // Make sure username field is not null
        if (userUN == null) {
            Log.e("LogInActivity", "Username was null");
            return;
        }

        // Assign value entered by user in password field to variable
        userPW = findViewById(R.id.et2);

        // Make sure password field is not null
        if (userPW == null) {
            Log.e("LogInActivity", "Password was null");
            return;
        }

        // When the button is pressed...
        login.setOnClickListener(view -> {

            String username = userUN.getText().toString().trim();
            String password = userPW.getText().toString().trim();

            // If either the username or password field are left blank
            if (username.isEmpty() || password.isEmpty()) {

                // Display a pop-up error message
                Toast.makeText(this, "Please enter both a username and password.", Toast.LENGTH_SHORT).show();
                return;

            }

            // Catch errors thrown by authenticator
            try {

                // If username and password are found in the login database
                if (db.authenticate(username, password)) {

                    // Switch to home activity (launch home screen)
                    Intent intent = new Intent(LogInActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
                // If either the username or password is unable to be authenticated
                else {

                    // Display a pop-up error message
                    Toast.makeText(this, "Invalid username or password.", Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {

                //Log error
                Log.e("LogInActivity", "Authentication failed: " + e.getMessage());
                //Display a pop-up error message
                Toast.makeText(this, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show();

            }

        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }



}
