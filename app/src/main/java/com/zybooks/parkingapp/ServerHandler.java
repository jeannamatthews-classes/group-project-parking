package com.zybooks.parkingapp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerHandler {


    public interface JsonCallback {
        void onSuccess(String json);
        void onError(String error);
    }

    public static void fetchParkingStatus(JsonCallback callback) {
        new Thread(() -> {
            try {
                URL url = new URL("http://128.153.144.130:5001/parking-status"); //where connect to machine locally

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) { //adds json to line
                    result.append(line);
                }

                reader.close();

                callback.onSuccess(result.toString());

            } catch (Exception e) {
                callback.onError(e.getMessage());
            }
        }).start();
    }
}