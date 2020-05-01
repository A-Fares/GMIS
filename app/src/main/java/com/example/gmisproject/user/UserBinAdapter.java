package com.example.gmisproject.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.BinsModel;
import com.example.gmisproject.R;

import java.util.ArrayList;

public class UserBinAdapter extends RecyclerView.Adapter<UserBinAdapter.UserBinHolder> {

    private ArrayList<BinsModel> binsModels;
    private OnItemClickListener mlistener;

    public UserBinAdapter(ArrayList<BinsModel> binsModels) {
        this.binsModels = binsModels;
    }

    @NonNull
    @Override
    public UserBinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bin_item, parent, false);
        UserBinHolder viewHolder = new UserBinHolder(v , mlistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserBinHolder holder, int position) {
        BinsModel currentBin = binsModels.get(position);


        holder.binId.setText(String.valueOf(currentBin.getBinId()));
        holder.clientAddress.setText(currentBin.getAddress());
        holder.binStatus.setText(currentBin.getStatus());
        holder.binPercentage.setProgress(currentBin.getPercentage());
    }

    @Override
    public int getItemCount() {
        return binsModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void SetOnItemClickListener(OnItemClickListener listener){
        mlistener = listener;
    }

    public static class UserBinHolder extends RecyclerView.ViewHolder {
        public ImageView trash;
        public TextView binId;
        public TextView clientAddress;
        public TextView binStatus;
        public ProgressBar binPercentage;

        public UserBinHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            trash = itemView.findViewById(R.id.trash);
            binId = itemView.findViewById(R.id.binId);
            clientAddress = itemView.findViewById(R.id.user_address);
            binStatus = itemView.findViewById(R.id.bin_status);
            binPercentage = itemView.findViewById(R.id.bin_percentage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }

                    }

                }
            });
        }
    }
}
