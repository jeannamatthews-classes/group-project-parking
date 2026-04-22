package com.zybooks.parkingapp;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

// Home_Fragment.java
public class Home_Fragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use a simple layout for now, like home_fragment.xml
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_home);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // 1. Get the shared data from the repository
        ArrayList<Lot> dataForAdapter = ParkingRepository.getInstance().getParkingLots();

        // 2. Give that data to the adapter
        lot_adapter adapter = new lot_adapter(dataForAdapter);

        // 3. Attach to the RecyclerView that exists in THIS fragment
        recyclerView.setAdapter(adapter);


        return view;
    }
}