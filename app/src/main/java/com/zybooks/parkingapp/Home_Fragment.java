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

        //Parking Lots Data
        ArrayList<Lot> parkingLots = new ArrayList<>();
        parkingLots.add(new Lot("Cheel Parking Lot",5,60,44.6648034,-75.000313));
        parkingLots.add(new Lot("Lower Cheel Parking Lot",5,60,44.6644692,-75.0030064));;
        parkingLots.add(new Lot("Moore Parking Lot",5,60,44.6629877,-74.9978577));
        parkingLots.add(new Lot("WoodStock Parking Low",5,60,44.6616051,-74.996133));
        parkingLots.add(new Lot("Town Houses Parking Lot",5,60,44.6607585,-75.0015777));
        parkingLots.add(new Lot("Hamlin-Powers Parking Lot",5,60,44.6647109,-74.994961));

        lot_adapter adapter = new lot_adapter(parkingLots);


        recyclerView.setAdapter(adapter);


        return view;
    }
}
