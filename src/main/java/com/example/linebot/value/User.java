package com.example.linebot.value;

import java.sql.Timestamp;

public class User {

    private final String userId;
    private final Timestamp timestamp;
    private final String displayName;

    public User(String userId, Timestamp timestamp, String displayName) {
        this.userId = userId;
        this.timestamp = timestamp;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

}
