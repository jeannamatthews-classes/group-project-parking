package com.zybooks.parkingapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

public class LotCardClickHandler {
    public LotCardClickHandler(Activity activity) {

        View lotCardsView = activity.findViewById(R.id.lotCards);

        MaterialCardView lotCardA = lotCardsView.findViewById(R.id.lotCardA);
        MaterialCardView lotCardB = lotCardsView.findViewById(R.id.lotCardB);
        MaterialCardView lotCardC = lotCardsView.findViewById(R.id.lotCardC);
        MaterialCardView lotCardD = lotCardsView.findViewById(R.id.lotCardD);
        MaterialCardView lotCardE = lotCardsView.findViewById(R.id.lotCardE);
        MaterialCardView lotCardF = lotCardsView.findViewById(R.id.lotCardF);

        // Replace the coordinates with each lot's real location
        lotCardA.setOnClickListener(v -> openMap(activity, 44.6648034,-75.000313, "Cheel Parking Lot"));
        lotCardB.setOnClickListener(v -> openMap(activity, 44.6644692,-75.0030064, "Cheel (Behind) Parking Lot"));
        lotCardC.setOnClickListener(v -> openMap(activity, 44.6629877,-74.9978577, "Morehouse/Sneel Hall Parking Lot"));
        lotCardD.setOnClickListener(v -> openMap(activity, 44.6616051,-74.996133, "Woodstock Parking Lot"));
        lotCardE.setOnClickListener(v -> openMap(activity, 44.6607585, -75.0015777, "Townhouses Parking Lot"));
        lotCardF.setOnClickListener(v -> openMap(activity, 44.6647109,-74.994961, "Hamlin-Powers Parking Lot"));
    }



    private void openMap(Context context, double latitude, double longitude, String lotName) {

        new AlertDialog.Builder(context)
                .setTitle("Open Map")
                .setMessage("Do you want to continue Google Maps for:\n" + lotName + "?")
                .setPositiveButton("Continue", (dialog, which) -> {

                    String url = "https://www.google.com/maps?q="
                            + latitude + "," + longitude + "(" + lotName + ")";

                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    context.startActivity(browserIntent);
                })
                .setNegativeButton("No", null)
                .show();
    }
}