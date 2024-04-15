package com.example.task5_1c;

import java.util.List;

public class User {
    private String username;
    private String password;
    private List<String> playlist;

    public User(String username, String password, List<String> playlist) {
        this.username = username;
        this.password = password;
        this.playlist = playlist;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<String> playlist) {
        this.playlist = playlist;
    }
}