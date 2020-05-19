package com.example.gmisproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BinsAdapter extends RecyclerView.Adapter<BinsAdapter.EmpBinHolder> {

    private ArrayList<BinsModel> binsModels;
    private OnItemClickListener mListener;

    public BinsAdapter(ArrayList<BinsModel> empBinsList) {
        this.binsModels = empBinsList;
    }

    @NonNull
    @Override
    public EmpBinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bin_item_emp, parent, false);
        BinsAdapter.EmpBinHolder viewHolder = new BinsAdapter.EmpBinHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpBinHolder holder, int position) {
        BinsModel currentBin = binsModels.get(position);
        holder.clientAddress.setText(currentBin.getAddress());
        holder.binId.setText(String.valueOf(currentBin.getBinId()));
        holder.binStatus.setText(currentBin.getStatus());
        holder.binPercentage.setProgress(currentBin.getPercentage());
    }

    @Override
    public int getItemCount() {
        return binsModels.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public class EmpBinHolder extends RecyclerView.ViewHolder {
        public ProgressBar binPercentage;
        public TextView binId;
        public TextView binStatus;
        public TextView clientAddress;

        public EmpBinHolder(@NonNull View itemView) {
            super(itemView);
            binId = itemView.findViewById(R.id.bin_id);
            binStatus = itemView.findViewById(R.id.bin_status);
            binPercentage = itemView.findViewById(R.id.progress_bin);
            clientAddress = itemView.findViewById(R.id.client_address);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}
