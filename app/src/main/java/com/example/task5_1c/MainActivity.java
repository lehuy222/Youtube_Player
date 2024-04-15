package com.example.task5_1c;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    EditText usernameEt, passwordEt;
    Button loginBt, registerBt;
    DatabaseHelper db = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        usernameEt = findViewById(R.id.usernameEt);
        passwordEt = findViewById(R.id.passwordEt);

        loginBt = findViewById(R.id.loginBt);
        registerBt = findViewById(R.id.registerBt);

        registerBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        loginBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }
    public void login(){
        User check_available = db.getUserByUsername(usernameEt.getText().toString());
        if(Objects.equals(check_available.getUsername(), usernameEt.getText().toString()) && Objects.equals(check_available.getPassword(), passwordEt.getText().toString())){
            Intent intent = new Intent(MainActivity.this, UserMainActivity.class);
            intent.putExtra("USERNAME", usernameEt.getText().toString());
            startActivity(intent);
        }
    }
}