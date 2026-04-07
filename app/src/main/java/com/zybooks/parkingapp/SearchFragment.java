package com.zybooks.parkingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private RecyclerView recyclerView;
    private LotAdapter adapter;
    private List<Lot> fullLotList; // The original data
    private List<Lot> filteredList; // What we show while typing

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        // 1. Setup RecyclerView
        recyclerView = view.findViewById(R.id.search_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 2. Initialize your lists (You'd likely get this from a Database or Repository)
        fullLotList = getParkingLots();
        filteredList = new ArrayList<>(fullLotList);

        // 3. Setup Adapter
        adapter = new LotAdapter(filteredList);
        recyclerView.setAdapter(adapter);

        // 4. Setup Search Logic
        SearchView searchView = view.findViewById(R.id.fragment_search_bar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return view;
    }

    private void filter(String text) {
        filteredList.clear();
        for (Lot lot : fullLotList) {
            if (lot.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(lot);
            }
        }
        adapter.notifyDataSetChanged(); // Tells the list to refresh
    }

    // Dummy data for now
    private List<Lot> getParkingLots() {
        List<Lot> list = new ArrayList<>();
        list.add(new Lot("North Lot", "Available"));
        list.add(new Lot("South Garage", "Full"));
        return list;
    }
}