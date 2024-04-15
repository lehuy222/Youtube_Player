package com.example.task5_1c;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShowPlaylistActivity extends AppCompatActivity {
    DatabaseHelper db = new DatabaseHelper(this);
    User user;
    ListView listView;
    Button backButton;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_playlist);

        backButton = findViewById(R.id.backButton);
        listView = findViewById(R.id.listViewPlaylist);
        String userName = getIntent().getStringExtra("USERNAME");
        user = db.getUserByUsername(userName);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, user.getPlaylist());
        listView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}