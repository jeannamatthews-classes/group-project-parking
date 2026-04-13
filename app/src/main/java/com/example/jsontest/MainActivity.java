package com.example.jsontest;

//andriod classes
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.TextView;
//json classes
import org.json.JSONArray;
import org.json.JSONObject;
//network/reading data
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
//handler
import android.os.Handler;

public class MainActivity extends AppCompatActivity { //home page

    TextView parkingInfoText;
    Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //connect java file to activity_main.xml

        parkingInfoText = findViewById(R.id.parkingInfoText); //link app varibles to UI elements in XML
        refreshButton = findViewById(R.id.refreshButton);

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitAll().build()); //allow wifi for example

        fetchParkingData(); //loads data as soon as app opened

        refreshButton.setOnClickListener(v -> fetchParkingData());
    }

    private void fetchParkingData() {
        try {
            URL url = new URL("http://10.0.2.2:5000/parking-status"); //10.0.2.2 is my computer
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); //connect
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            ); //reading server response

            StringBuilder result = new StringBuilder(); //store response
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            reader.close();

            JSONObject json = new JSONObject(result.toString()); //converts response text to json object

            String lotName = json.getString("lot_name"); //now can get lot name, numbers, spots, etc, from JSON
            int availableSpots = json.getInt("available_spots");

            JSONArray spotsArray = json.getJSONArray("spots");

            StringBuilder display = new StringBuilder(); //for the text display
            display.append("Lot: ").append(lotName).append("\n"); //add lot and spot count
            display.append("Available Spots: ").append(availableSpots).append("\n\n");

            for (int i = 0; i < spotsArray.length(); i++) { //loops through each spot in JSON array
                JSONObject spot = spotsArray.getJSONObject(i);
                String id = spot.getString("id");
                boolean occupied = spot.getBoolean("occupied");

                display.append(id)
                        .append(": ")
                        .append(occupied ? "Occupied" : "Available")
                        .append("\n");
            }

            parkingInfoText.setText(display.toString());

        } catch (Exception e) { //error handling
            e.printStackTrace();
            parkingInfoText.setText("Error loading parking data.");
        }
    }

}