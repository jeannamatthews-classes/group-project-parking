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

    private ArrayList<Lot> lotList;

    // Constructor to receive the data from the Fragment
    public lot_adapter(ArrayList<Lot> lotList) {
        this.lotList = lotList;
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
        Lot currentLot = lotList.get(position);
        double[] location = currentLot.getLocation();

        // Set the text from your Lot object to the TextViews
        holder.nameTextView.setText(currentLot.getLotName());
        holder.usageTextView.setText(currentLot.getStatus()); // e.g., "5/60"

        holder.itemView.setOnClickListener(v -> {
            // 1. Get the context (needed to start fragment transactions)
            AppCompatActivity activity = (AppCompatActivity) v.getContext();

            // 2. Create the new fragment
            LotDetailFragment detailFragment = new LotDetailFragment();

            // 3. Pass the data (Name, Usage, etc.) via a Bundle
            Bundle args = new Bundle();
            args.putString("lotName", currentLot.getLotName());
            args.putString("lotUsage", currentLot.getStatus());
            args.putDouble("lat", location[0]); // Double check: Is index 0 latitude?
            args.putDouble("lon", location[1]); // Double check: Is index 1 longitude?
            detailFragment.setArguments(args);

            // 4. Perform the swap
            activity.getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, detailFragment)
                    .addToBackStack(null) // Allows user to go "Back" to the list
                    .commit();
        });
    }

    @Override
    public int getItemCount() {
        return lotList.size();
    }

    // This class finds the views inside item_lot.xml
    public static class LotViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView usageTextView;

        public LotViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textViewLotName);
            usageTextView = itemView.findViewById(R.id.textViewLotStatus);
        }
    }
    public interface OnLotClickListener {
        void onLotClick(Lot lot);
    }
}