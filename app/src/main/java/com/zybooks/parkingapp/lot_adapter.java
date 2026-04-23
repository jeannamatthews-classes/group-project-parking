package com.zybooks.parkingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class lot_adapter extends RecyclerView.Adapter<lot_adapter.LotViewHolder> {

    public interface OnLotClickListener {
        void onLotClick(Lot lot);
    }

    private ArrayList<Lot> LotList;
    private OnLotClickListener listener;

    // Constructor to receive the data from the Fragment
    public lot_adapter(ArrayList<Lot> lotList) {
        this.LotList  = lotList;
    }

    public void UpdateData( ArrayList<Lot> NewList) {
        this.LotList = NewList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public LotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // This links the adapter to your "Card" layout

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_lot, parent, false);
        return new LotViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LotViewHolder holder, int position) {
        // Get the specific lot for this row
        Lot currentLot = LotList.get(position);

        // Set the text from your Lot object to the TextViews
        holder.nameTextView.setText(currentLot.getLotName());
        holder.usageTextView.setText(currentLot.getStatus()); // e.g., "5/60"

        double fullness = (double) currentLot.getFillPercentage();

        int strokeColor;
        if (fullness >= 0.90) {
            // More than 90% full - Red
            strokeColor = androidx.core.content.ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_red_dark);
        } else if (fullness >= 0.50) {
            // Between 50% and 90% - Yellow/Orange
            strokeColor = androidx.core.content.ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_light);
        } else {
            // Less than 50% - Green
            strokeColor = androidx.core.content.ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_light);
        }

        // Apply the color to the card outline
        holder.lotCard.setStrokeColor(strokeColor);

        holder.itemView.setOnClickListener(v -> {

            SecurePrefs.putString("selected_lot_name",  currentLot.getLotName());
            SecurePrefs.putString("selected_lot_usage", currentLot.getStatus());
            SecurePrefs.putString("selected_lot_lat",   String.valueOf(currentLot.getLatitude()));
            SecurePrefs.putString("selected_lot_lon",   String.valueOf(currentLot.getLongitude()));


            // Recent get the name of the Lot Name
            Recents_Manager.addRecent(currentLot.getLotName());

            // Get the context
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            //  Create the new fragment
            LotDetailFragment detailFragment = new LotDetailFragment();

            // Pass the data in the bundle
            Bundle args = new Bundle();
            args.putString("lotName", currentLot.getLotName());
            args.putString("lotUsage", currentLot.getStatus());
            args.putDouble("lat",currentLot.getLatitude() );
            args.putDouble("lon", currentLot.getLongitude());
            detailFragment.setArguments(args);

            // Swap element
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return LotList.size();
    }

    // This class finds the views inside item_lot.xml
    public static class LotViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usageTextView;

        com.google.android.material.card.MaterialCardView lotCard;
        public LotViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewLotName);
            usageTextView = itemView.findViewById(R.id.textViewLotStatus);
            lotCard = itemView.findViewById(R.id.lotCard);
        }
    }
    }