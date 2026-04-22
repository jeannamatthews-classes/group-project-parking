package com.zybooks.parkingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String parkingJson; //added getter and setter for json string
    public void setParkingJson(String json) {
        this.parkingJson = json;
    }
    public String getParkingJson() {
        return parkingJson;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ServerHandler.fetchParkingStatus(new ServerHandler.JsonCallback() {

            @Override
            public void onSuccess(String json) {

                runOnUiThread(() -> {
                    parkingJson = json; // store it here
                    System.out.println(parkingJson);
                });
            }

            @Override
            public void onError(String error) {

                runOnUiThread(() -> {
                    System.out.println("ERROR: " + error);
                });
            }
        });



        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set the toolbar so we can add the toggle button
        setSupportActionBar(toolbar);

        // This creates the "hamburger" icon and links it to the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new Home_Fragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Handle clicks on the sidebar items
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment selectedFragment = null;
            if (id == R.id.nav_home) {
                selectedFragment = new Home_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();;
            }

            else if (id == R.id.nav_search) {
                // Handle Search click
                selectedFragment = new Search_Fragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }
            else if (id == R.id.nav_favorites) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Favorites_Fragment())
                        .addToBackStack(null)
                        .commit();

                // Handle Recent click it open the favorites
            }
            else if (id == R.id.nav_recent) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new Recents_Fragment())
                        .addToBackStack(null)
                        .commit();
                // Handle Recent click it open the recents
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            }

            // Close the drawer after a selection is made
            drawerLayout.closeDrawers();
            return true;
        });
    }
}