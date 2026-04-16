package com.zybooks.parkingapp;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;

public class Search_Fragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private ArrayList<String> parkingLocations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment, container, false);

        // 1. Initialize Mock Data (Replace this with a Database/API call later)
        parkingLocations = new ArrayList<>(Arrays.asList(
                "North Garage - Section A",
                "South Lot - Near Library",
                "East Deck - Level 2",
                "Downtown Public Parking",
                "Campus West Overflow",
                "Science Center Reserved"
        ));

        // 2. Set up the ListView and Adapter
        ListView listView = view.findViewById(R.id.search_results_list);
        adapter = new ArrayAdapter<>(getContext(), R.layout.list_item_white, parkingLocations);
        listView.setAdapter(adapter);

        // 3. Set up the Search Logic
        SearchView searchView = view.findViewById(R.id.search_view_fragment);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // This triggers when the user presses the "Search" button on the keyboard
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // This triggers every time a character is typed
                adapter.getFilter().filter(newText);
                return true;
            }
        });

        return view;
    }
}
