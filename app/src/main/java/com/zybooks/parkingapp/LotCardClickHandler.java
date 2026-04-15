package com.zybooks.parkingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
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
        lotCardA.setOnClickListener(v -> openMap(activity, 44.6969, -74.9870, "Lot A"));
        lotCardB.setOnClickListener(v -> openMap(activity, 44.6970, -74.9871, "Lot B"));
        lotCardC.setOnClickListener(v -> openMap(activity, 44.6971, -74.9872, "Lot C"));
        lotCardD.setOnClickListener(v -> openMap(activity, 44.6972, -74.9873, "Lot D"));
        lotCardE.setOnClickListener(v -> openMap(activity, 44.6973, -74.9874, "Lot E"));
        lotCardF.setOnClickListener(v -> openMap(activity, 44.6974, -74.9875, "Lot F"));
    }

    private void openMap(Context context, double latitude, double longitude, String lotName) {
        // This opens Google Maps in the browser
        String url = "https://www.google.com/maps?q=" + latitude + "," + longitude + "(" + lotName + ")";
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }
}