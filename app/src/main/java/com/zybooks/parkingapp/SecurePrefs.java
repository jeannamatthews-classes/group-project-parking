package com.zybooks.parkingapp;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKey;

import java.io.IOException;
import java.security.GeneralSecurityException;

import java.util.HashSet;
import java.util.Set;

public class SecurePrefs {

    private static SharedPreferences prefs;

    // Call this once before using get/set methods
    public static void init(Context context) {
        try {
            MasterKey masterKey = new MasterKey.Builder(context)
                    .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                    .build();

            prefs = EncryptedSharedPreferences.create(context, "secret_prefs",   // name of the encrypted file
                    masterKey,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );

        } catch (GeneralSecurityException | IOException e) {
            e.printStackTrace();
        }
    }

    // --- Save values ---
    public static void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public static void putBoolean(String key, boolean value) {
        prefs.edit().putBoolean(key, value).apply();
    }

    public static void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    // Read values
    public static String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }


    // DOES ARE METHODS THAT WE USE just in case
    public static int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    // Clear everything
    public static void clear() {
        prefs.edit().clear().apply();
    }


// Add these to SecurePrefs.java

    public static void putStringSet(String key, Set<String> value) {
        prefs.edit().putStringSet(key, new HashSet<>(value)).apply();
    }

    public static Set<String> getStringSet(String key, Set<String> defaultValue) {
        Set<String> result = prefs.getStringSet(key, defaultValue);
        return result != null ? new HashSet<>(result) : new HashSet<>(defaultValue);
        // We wrap in a new HashSet because EncryptedSharedPreferences returns
        // an unmodifiable reference — editing it directly would cause a crash
    }
}
