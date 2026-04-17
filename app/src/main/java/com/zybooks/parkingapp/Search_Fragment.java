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
        ArrayList<Lot> parkingLots = new ArrayList<>();
        parkingLots.add(new Lot("Cheel Parking Lot", 5, 60, 44.6648034, -75.000313));
        parkingLots.add(new Lot("Lower Cheel Parking Lot", 5, 60, 44.6644692, -75.0030064));;
        parkingLots.add(new Lot("Moore Parking Lot", 5, 60, 44.6629877, -74.9978577));
        parkingLots.add(new Lot("WoodStock Parking Low", 5, 60, 44.6616051, -74.996133));
        parkingLots.add(new Lot("Town Houses Parking Lot", 5, 60, 44.6607585, -75.0015777));
        parkingLots.add(new Lot("Hamlin-Powers Parking Lot", 5, 60, 44.6647109, -74.994961));;

        parkingLocations = new ArrayList<>();
        for (Lot lot : parkingLots) {
            parkingLocations.add(lot.getLotName());
        }

        // 2. Set up the ListView and Adapter
        ListView listView = view.findViewById(R.id.search_results_list);
        adapter = new ArrayAdapter<>(getContext(), R.layout.list_item_white, parkingLocations);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view1, position, id) -> {
            // 1. Get the name of the lot that was clicked
            String selectedName = (String) parent.getItemAtPosition(position);

            // 2. Find the Lot object that matches this name
            Lot matchedLot = null;
            for (Lot lot : parkingLots) { // using the parkingLots list from your screenshot
                if (lot.getLotName().equals(selectedName)) {
                    matchedLot = lot;
                    break;
                }
            }

            if (matchedLot != null) {
                // 3. Prepare the bundle
                Bundle args = new Bundle();
                args.putString("lotName", matchedLot.getLotName());
                args.putString("lotUsage", "Availability: " + matchedLot.getLotnumber() + " / " + matchedLot.getTotalLotnumber());

                // Pass the coordinates from your new Lot object
                args.putDouble("lat", matchedLot.getLocation()[0]);
                args.putDouble("lon", matchedLot.getLocation()[1]);

                // 4. Open the Detail Fragment
                LotDetailFragment detailFragment = new LotDetailFragment();
                detailFragment.setArguments(args);

                getParentFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, detailFragment)
                        .addToBackStack(null)
                        .commit();
            }
                });


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
