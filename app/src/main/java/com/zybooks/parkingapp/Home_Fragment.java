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
        // Use a simple layout for now
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ServerHandler.fetchParkingStatus(new ServerHandler.JsonCallback() {
            @Override
            public void onSuccess(String json) {
                requireActivity().runOnUiThread(() -> {
                    // send data to ui?
                    ((MainActivity) requireActivity()).setParkingJson(json);
                    System.out.println(json); //delete after testing
                });
            }

            @Override
            public void onError(String error) {
                requireActivity().runOnUiThread(() -> {
                    System.out.println("ERROR: " + error);
                });
            }
        });


        return view;
    }
}
