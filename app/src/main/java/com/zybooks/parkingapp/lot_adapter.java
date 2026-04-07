package com.zybooks.parkingapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LotAdapter extends RecyclerView.Adapter<LotAdapter.LotViewHolder> {

    private List<Lot> lotList;

    // Constructor: Takes the list of data
    public LotAdapter(List<Lot> lotList) {
        this.lotList = lotList;
    }

    // 1. Tell the adapter which XML file to use for each row
    @NonNull
    @Override
    public LotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_lot, parent, false);
        return new LotViewHolder(view);
    }

    // 2. Put the data from the Lot object into the TextViews
    @Override
    public void onBindViewHolder(@NonNull LotViewHolder holder, int position) {
        Lot currentLot = lotList.get(position);
        holder.lotName.setText(currentLot.getName());
        holder.lotStatus.setText(currentLot.getStatus());
    }

    @Override
    public int getItemCount() {
        return lotList.size();
    }

    // 3. The "Update" method we used in the filter logic
    public void updateList(List<Lot> newList) {
        this.lotList = newList;
        notifyDataSetChanged(); // Refreshes the list on screen
    }

    // ViewHolder: Finds the views inside item_lot.xml
    public static class LotViewHolder extends RecyclerView.ViewHolder {
        TextView lotName, lotStatus;

        public LotViewHolder(@NonNull View itemView) {
            super(itemView);
            lotName = itemView.findViewById(R.id.text_lot_name);
            lotStatus = itemView.findViewById(R.id.text_lot_status);
        }
    }
}