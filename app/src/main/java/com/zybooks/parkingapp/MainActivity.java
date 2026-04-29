package com.zybooks.parkingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String json;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServerHandler.fetchParkingStatus(new ServerHandler.JsonCallback() {
            @Override
            public void onSuccess(String json) {
                runOnUiThread(() -> {
                    System.out.println("RAW JSON RESPONSE:");
                    System.out.println(json);

                    Gson gson = new Gson();
                    Type listType = new TypeToken<ArrayList<Lot>>(){}.getType();

                    List<Lot> tempLots = gson.fromJson(json, listType);

                    if (tempLots != null) {
                        ParkingRepository.getInstance()
                                .setParkingLots(new ArrayList<>(tempLots));
                    }
            });
            }

            @Override
            public void onError(String error) {

                runOnUiThread(() -> {
                    System.out.println("ERROR: " + error);
                });
            }
        });


//        Gson gson = new Gson();
//        Type ListType = new TypeToken<ArrayList<Lot>>(){}.getType();
//
//        List<Lot> tempLots = gson.fromJson(jsonResponse, ListType);
//        if (tempLots != null) {
//            ArrayList<Lot> parkinglots = new ArrayList<>(tempLots);
//            ParkingRepository.getInstance().setParkingLots(parkinglots);
//        }



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