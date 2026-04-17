package com.zybooks.parkingapp;

import android.app.Application;

public class Private_Data extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SecurePrefs.init(this); // Runs once when the app starts
    }
}