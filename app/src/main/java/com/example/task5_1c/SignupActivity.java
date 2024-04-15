package com.example.task5_1c;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

public class SignupActivity extends AppCompatActivity {

    private EditText fullNameEditText;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button createAccountButton;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize views
        fullNameEditText = findViewById(R.id.fullNameEditText);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        createAccountButton = findViewById(R.id.createAccountButton);

        // Set up the button click listener
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {
        // Get the values from the EditText fields
        String fullName = fullNameEditText.getText().toString().trim();
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        // Validate the inputs
        if (validateInputs(fullName, username, password, confirmPassword)) {
            db.addUser(new User(username, password, Arrays.asList()));
            Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    private boolean validateInputs(String fullName, String username, String password, String confirmPassword) {
        if (fullName.isEmpty()) {
            showToast("Full name cannot be empty");
            return false;
        }
        if (username.isEmpty()) {
            showToast("Username cannot be empty");
            return false;
        }
        if (password.isEmpty()) {
            showToast("Password cannot be empty");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            showToast("Passwords do not match");
            return false;
        }
        if (password.length() < 8) {
            showToast("Password must be at least 8 characters long");
            return false;
        }
        User check_available = db.getUserByUsername(usernameEditText.getText().toString());
        if(check_available != null){
            showToast("Username already exits. Please choose another name!");
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}