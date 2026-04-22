package com.example.jsontest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView jsonTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jsonTextView = findViewById(R.id.jsonTextView);

        // allow network on main thread (OK for testing only)
        StrictMode.setThreadPolicy(
                new StrictMode.ThreadPolicy.Builder().permitAll().build()
        );

        fetchRawJson();
    }

    private void fetchRawJson() {
        try {
            // IMPORTANT:
            // 10.0.2.2 = your computer from Android emulator
            URL url = new URL("http://10.0.2.2:5001/parking-status");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

            // DISPLAY RAW JSON
            jsonTextView.setText(result.toString());

        } catch (Exception e) {
            e.printStackTrace();
            jsonTextView.setText("ERROR:\n" + e.getMessage());
        }
    }
}