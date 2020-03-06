package com.example.gmisproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmpBinAdapter extends RecyclerView.Adapter<EmpBinAdapter.EmpBinHolder> {

    private ArrayList<EmpBin> empBins;

    public EmpBinAdapter(ArrayList<EmpBin> empBinsList) {
        this.empBins = empBinsList;
    }

    @NonNull
    @Override
    public EmpBinHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.bin_item_emp, parent, false);
        EmpBinAdapter.EmpBinHolder viewHolder = new EmpBinAdapter.EmpBinHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EmpBinHolder holder, int position) {
        EmpBin currentBin = empBins.get(position);
        holder.clientAddress.setText(currentBin.getClientAddress());
        holder.binId.setText(currentBin.getBinID());
        holder.binPercentage.setProgress(currentBin.getBinPercentage());
        holder.binStatus.setText(currentBin.getBinStatus());
    }

    @Override
    public int getItemCount() {
        return empBins.size();
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
        }
    }
}
