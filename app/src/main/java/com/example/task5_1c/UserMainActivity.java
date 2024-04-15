package com.example.task5_1c;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.task5_1c.databinding.ActivityUserMainBinding;

import java.util.List;

public class UserMainActivity extends AppCompatActivity {

    DatabaseHelper db = new DatabaseHelper(this);
    EditText urlEt;
//
    Button buttonPlay, buttonAdd, buttonView;
    List<String> playlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        String username = getIntent().getStringExtra("USERNAME");
        urlEt = findViewById(R.id.urlEt);
        buttonPlay = findViewById(R.id.buttonPlay);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonView = findViewById(R.id.buttonView);

        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, PlayActivity.class);

                intent.putExtra("URL", urlEt.getText().toString());
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String new_video = urlEt.getText().toString();
                User tmp_user = db.getUserByUsername(username);
                playlist = tmp_user.getPlaylist();
                if (!new_video.isEmpty()) {
                    playlist.add(new_video);
                    urlEt.setText("");
                } else {
                    Toast.makeText(UserMainActivity.this, "Please enter a URL", Toast.LENGTH_SHORT).show();
                }
                db.updateUserPlaylist(username, playlist);
            }
        });

        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserMainActivity.this, ShowPlaylistActivity.class);

                intent.putExtra("USERNAME", username);
                startActivity(intent);
            }
        });
    }

}