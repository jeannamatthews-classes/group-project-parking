package com.zybooks.parkingapp;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class Recents_Fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recents, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // lot list
        ArrayList<Lot> allLots = new ArrayList<>();
        allLots.add(new Lot("Cheel Parking Lot",5,60,44.6648034,-75.000313));
        allLots.add(new Lot("Lower Cheel Parking Lot",5,60,44.6644692,-75.0030064));
        allLots.add(new Lot("Moore Parking Lot",5,60,44.6629877,-74.9978577));
        allLots.add(new Lot("WoodStock Parking Low",5,60,44.6616051,-74.996133));
        allLots.add(new Lot("Town Houses Parking Lot",5,60,44.6607585,-75.0015777));
        allLots.add(new Lot("Hamlin-Powers Parking Lot",5,60,44.6647109,-74.994961));

        // Get the ordered list of recently viewed lot names
        List<String> recentNames = Recents_Manager.getRecents();

        //  most recent first
        ArrayList<Lot> recentLots = new ArrayList<>();
        for (String name : recentNames) {
            for (Lot lot : allLots) {
                if (lot.getLotName().equals(name)) {
                    recentLots.add(lot);
                    break;
                }
            }
        }

        lot_adapter adapter = new lot_adapter(recentLots);
        recyclerView.setAdapter(adapter);

        return view;
    }
}