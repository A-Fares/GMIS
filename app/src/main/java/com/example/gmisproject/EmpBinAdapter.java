package com.example.gmisproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EmpBinAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private ArrayList<EmpBin> empBins;

    public EmpBinAdapter(ArrayList<EmpBin> empBinsList) {

        this.empBins = empBinsList;
    }

    @Override
    public int getItemViewType(int position) {
        switch (empBins.get(position).getViewType()) {
            case 0:
                return EmpBin.PUBLIC_BIN;
            case 1:
                return EmpBin.PRIVATE_BIN;
            default:
                return -1;
        }
       /* if (position == EmpBin.PUBLIC_BIN)
            return EmpBin.PUBLIC_BIN;
        else
            return EmpBin.PRIVATE_BIN;*/
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      /*  View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.private_bin_item, parent, false);
        EmpBinPublicHolder empBinHolderPublic = new EmpBinPublicHolder(v);
        return empBinHolderPublic;*/
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;

        if (viewType == EmpBin.PUBLIC_BIN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.public_bin_item, parent, false);
            viewHolder = new EmpBinPublicHolder(view);
        } else if (viewType == EmpBin.PRIVATE_BIN) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.private_bin_item, parent, false);
            viewHolder = new EmpBinPrivateHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

        if (holder.getItemViewType() == EmpBin.PUBLIC_BIN) {

            final EmpBin currentItem = empBins.get(position);

            ((EmpBinPublicHolder) holder).binNum.setText(currentItem.getBinID());
            ((EmpBinPublicHolder) holder).binStatus.setText(currentItem.getBinStatus());
            ((EmpBinPublicHolder) holder).binPercentage.setProgress(currentItem.getBinPercentage());
        } else if (holder.getItemViewType() == EmpBin.PRIVATE_BIN) {
            final EmpBin currentItem = empBins.get(position);
            ((EmpBinPrivateHolder) holder).binNum.setText(currentItem.getBinID());
            ((EmpBinPrivateHolder) holder).binStatus.setText(currentItem.getBinStatus());
            ((EmpBinPrivateHolder) holder).binPercentage.setProgress(currentItem.getBinPercentage());
            ((EmpBinPrivateHolder) holder).clientAddress.setText(currentItem.getClientAddress());
            ((EmpBinPrivateHolder) holder).clientPhone.setText(currentItem.getClientPhone());
        }
    }


    @Override
    public int getItemCount() {
        return empBins.size();
    }

    public class EmpBinPublicHolder extends RecyclerView.ViewHolder {
        public ProgressBar binPercentage;
        public TextView binNum;
        public TextView binStatus;

        public EmpBinPublicHolder(@NonNull View itemView) {
            super(itemView);
            binNum = itemView.findViewById(R.id.bin_number_data);
            binStatus = itemView.findViewById(R.id.bin_status_data);
            binPercentage = itemView.findViewById(R.id.progress_bin_public);
        }
    }

    public class EmpBinPrivateHolder extends RecyclerView.ViewHolder {
        public ProgressBar binPercentage;
        public TextView binNum;
        public TextView binStatus;
        public TextView clientAddress;
        public TextView clientPhone;

        public EmpBinPrivateHolder(@NonNull View itemView) {
            super(itemView);
            binNum = itemView.findViewById(R.id.bin_number_data);
            binStatus = itemView.findViewById(R.id.bin_status_data);
            binPercentage = itemView.findViewById(R.id.progress_bin_private);
            clientAddress = itemView.findViewById(R.id.client_address);
            clientPhone = itemView.findViewById(R.id.client_phone);
        }
    }

}
