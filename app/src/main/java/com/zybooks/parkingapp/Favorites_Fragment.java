package com.zybooks.parkingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Set;

public class Favorites_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // 1. Get the shared data from the repository
        ArrayList<Lot> allLots = ParkingRepository.getInstance().getParkingLots();

        // Only keep the ones the user has clicked
        Set<String> favorites = Favorites_Manager.getFavorites();
        ArrayList<Lot> favoritedLots = new ArrayList<>();
        for (Lot lot : allLots) {
            if (favorites.contains(lot.getLotName())) {
                favoritedLots.add(lot);
            }
        }

        lot_adapter adapter = new lot_adapter(favoritedLots);
        recyclerView.setAdapter(adapter);

        return view;
    }
}