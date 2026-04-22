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

        //Lot List
        ArrayList<Lot> allLots = ParkingRepository.getInstance().getParkingLots();

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