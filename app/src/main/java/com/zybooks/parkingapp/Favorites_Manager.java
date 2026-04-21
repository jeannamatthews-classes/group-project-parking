package com.zybooks.parkingapp;

import java.util.HashSet;
import java.util.Set;

public class Favorites_Manager {

    private static final String KEY = "favorite_lots";

    public static Set<String> getFavorites() {
        return SecurePrefs.getStringSet(KEY, new HashSet<>());
    }

    public static void addFavorite(String lotName) {
        Set<String> favs = getFavorites();
        favs.add(lotName);
        SecurePrefs.putStringSet(KEY, favs);
    }

    public static void removeFavorite(String lotName) {
        Set<String> favs = getFavorites();
        favs.remove(lotName);
        SecurePrefs.putStringSet(KEY, favs);
    }

    public static boolean isFavorite(String lotName) {
        return getFavorites().contains(lotName);
    }
}