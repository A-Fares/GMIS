package com.example.gmisproject.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.R;

import java.util.ArrayList;

public class UserBinAdapter extends RecyclerView.Adapter<UserBinAdapter.UserBinHolder> {

    private ArrayList<UserBin> userBins;

    public UserBinAdapter(ArrayList<UserBin> userBins) {
        this.userBins = userBins;
    }

    @NonNull
    @Override
    public UserBinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bin_item, parent, false);
        UserBinHolder viewHolder = new UserBinHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserBinHolder holder, int position) {
        UserBin currentBin = userBins.get(position);

        holder.trash.setImageResource(currentBin.getBinImageId());
        holder.binId.setText(currentBin.getBinId());
        holder.userAddress.setText(currentBin.getUserAddress());
        holder.binStatus.setText(currentBin.getBinStatus());
        holder.binPercentage.setProgress(currentBin.getBinPercentage());
    }

    @Override
    public int getItemCount() {
        return userBins.size();
    }


    public static class UserBinHolder extends RecyclerView.ViewHolder {
        public ImageView trash;
        public TextView binId;
        public TextView userAddress;
        public TextView binStatus;
        public ProgressBar binPercentage;

        public UserBinHolder(@NonNull View itemView) {
            super(itemView);
            trash = itemView.findViewById(R.id.trash);
            binId = itemView.findViewById(R.id.bin_id);
            userAddress = itemView.findViewById(R.id.user_address);
            binStatus = itemView.findViewById(R.id.bin_status);
            binPercentage = itemView.findViewById(R.id.bin_percentage);
        }
    }
}
