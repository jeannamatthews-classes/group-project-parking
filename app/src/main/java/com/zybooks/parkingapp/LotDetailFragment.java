package com.zybooks.parkingapp;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LotDetailFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.lot_fragment, container, false);

        ImageButton backBtn = view.findViewById(R.id.back_button);
        backBtn.setOnClickListener(v -> {
                    // This is the magic line that goes back to the previous screen
                    if (getActivity() != null) {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
        });

        TextView nameTxt = view.findViewById(R.id.detailLotName);
        TextView usageTxt = view.findViewById(R.id.detailLotUsage);
        Button mapsBtn = view.findViewById(R.id.btnGoogleMaps);

        if (getArguments() != null) {
            String name = getArguments().getString("lotName");
            nameTxt.setText(name);
            usageTxt.setText("Availability: " + getArguments().getString("lotUsage"));

            mapsBtn.setOnClickListener(v -> {
                new androidx.appcompat.app.AlertDialog.Builder(getContext())
                        .setTitle("Open Map")
                        .setMessage("Do you want to continue Google Maps for:\n\n" + name + "?")

                        .setPositiveButton("Continue", (dialog, which) -> {

                            if (getArguments() != null) {
                                double lat = getArguments().getDouble("lat");
                                double lon = getArguments().getDouble("lon");

                                // Launch Google Maps using the lot name as a search query
                                String uri = "geo:" + lat + "," + lon + "?q=" + lat + "," + lon + "(" + nameTxt.getText().toString() + ")";
                                Intent mapintent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                                mapintent.setPackage("com.google.android.apps.maps");

                                try {
                                    startActivity(mapintent);
                                } catch (Exception e) {
                                    android.widget.Toast.makeText(getContext(), "Google Maps not found", android.widget.Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("No", null)
                        .show();
            });
        }
        return view;
    }
}
