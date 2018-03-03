package com.example.thea.wecare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ConsultMessage {

    private String title, description;

    public ConsultMessage(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
}
