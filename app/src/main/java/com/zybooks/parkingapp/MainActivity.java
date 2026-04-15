package com.zybooks.parkingapp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);




        // Set the toolbar so we can add the toggle button
        setSupportActionBar(toolbar);

        new LotCardClickHandler(this);

        // This creates the "hamburger" icon and links it to the drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        drawerLayout.addDrawerListener(toggle);

        toggle.syncState();


        // Handle clicks on the sidebar items
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_search) {
                // Handle Search click
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Search Parking Lots");

                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
//                        filter(newText);
                        return true;
                    }

                });
                return true;
            }
             else if (id == R.id.nav_favorites) {
                // Handle Favorites click
            }
             else if (id == R.id.nav_recent) {
                // Handle Recent click
            }

            // Close the drawer after a selection is made
            drawerLayout.closeDrawers();
            return true;
        });
    }





//    private void filter(String text) {
//        ArrayList<Lot> filteredlist = new ArrayList<>();
//        for (Lot item : lotList) {
//            if (item.getLotName().toLowerCase().contains(text.toLowerCase())) {
//                filteredlist.add(item);
//            }
//        }
//        LotAdapter.update(filteredlist);
//    }
//
//    public void Updatelist(List<Lot> newlist) {
//        this.lotlist = newlist;
//        LotAdapter.update(newlist);
//    }
}