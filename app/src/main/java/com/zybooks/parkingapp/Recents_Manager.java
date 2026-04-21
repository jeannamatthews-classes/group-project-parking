package com.zybooks.parkingapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Recents_Manager {

    private static final String KEY = "recent_lots";
    private static final int MAX = 5; // only keep last 5 spots

    public static void addRecent(String lotName) {
        List<String> recents = getRecents();

        // Remove it if already in the list so it moves to the front
        recents.remove(lotName);

        // Insert at the front (most recent first)
        recents.add(0, lotName);

        // max size it force to have only 5
        if (recents.size() > MAX) {
            recents = recents.subList(0, MAX);
        }

        // Save as comma-separated string
        SecurePrefs.putString(KEY, String.join(",", recents));
    }

    public static List<String> getRecents() {
        String raw = SecurePrefs.getString(KEY, "");
        if (raw.isEmpty()) return new ArrayList<>();
        // Split back into a list
        return new ArrayList<>(Arrays.asList(raw.split(",")));
    }

    // Not use it yet
    public static void clear() {
        SecurePrefs.putString(KEY, "");
    }
}