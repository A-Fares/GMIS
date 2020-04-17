package com.example.gmisproject.user;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmisproject.R;

import java.util.List;

import static com.example.gmisproject.user.UserMsgModel.COMPLAINT_MESSAGE;
import static com.example.gmisproject.user.UserMsgModel.INFORMATION_MESSAGE;
import static com.example.gmisproject.user.UserMsgModel.MAINTENANCE_MESSAGE;

public class UserMsgAdapter extends RecyclerView.Adapter {

    private List<UserMsgModel> msgModelsList;

    public UserMsgAdapter(List<UserMsgModel> msgModelsList) {
        this.msgModelsList = msgModelsList;
    }

    @Override
    public int getItemViewType(int position) {
       switch (msgModelsList.get(position).getViewType()){
           case 0:
               return INFORMATION_MESSAGE;
           case 1:
               return COMPLAINT_MESSAGE;
           case 2:
               return MAINTENANCE_MESSAGE;
           default:
               return -1;
       }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType){
            case INFORMATION_MESSAGE:
                View informationMsg= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.information_layout,viewGroup,false);
                return new InformationLayout(informationMsg);
            case COMPLAINT_MESSAGE:
                View complaintMsg= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.complaint_layout,viewGroup,false);
                return new ComplaintLayout(complaintMsg);
            case MAINTENANCE_MESSAGE:
                View maintenanceMsg= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.maintenace_layout,viewGroup,false);
                return new MaintenanceLayout(maintenanceMsg);
            default:
                return null;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (msgModelsList.get(position).getViewType()){
            case INFORMATION_MESSAGE:
                String empInfo=msgModelsList.get(position).getMsg();
                String empPhone=msgModelsList.get(position).getPhone();
                String empCost=msgModelsList.get(position).getCosts();
                ((InformationLayout)holder).setDataInfo(empInfo,empPhone,empCost);

                break;
            case COMPLAINT_MESSAGE:
                String complaintMsg=msgModelsList.get(position).getComplaintMsg();
                ((ComplaintLayout)holder).setComplaintMessage(complaintMsg);

                break;
            case MAINTENANCE_MESSAGE:
                String maintenanceMsg=msgModelsList.get(position).getMaintenanceMsg();
                String maintenanceMsgThank=msgModelsList.get(position).getMaintenanceMsgThank();
                ((MaintenanceLayout)holder).setMaintenanceMessage(maintenanceMsg,maintenanceMsgThank);

                break;
            default:
                return ;

        }
    }

    @Override
    public int getItemCount() {
        return msgModelsList.size();
    }

    class InformationLayout extends RecyclerView.ViewHolder{
        private TextView empInfo;
        private TextView empPhone;
        private TextView empCost;

        public InformationLayout(@NonNull View itemView) {
            super(itemView);
            empInfo=itemView.findViewById(R.id.emp_name);
            empPhone=itemView.findViewById(R.id.emp_phone_data);
            empCost=itemView.findViewById(R.id.cost_monthly_data);
        }
        private void setDataInfo(String msg,String phone,String cost){
            empInfo.setText(msg);
            empPhone.setText(phone);
            empCost.setText(cost);
        }
    }
    class ComplaintLayout extends RecyclerView.ViewHolder{
        private TextView complaintMessage;

        public ComplaintLayout(@NonNull View itemView) {
            super(itemView);
            complaintMessage=itemView.findViewById(R.id.complaint_message);
        }
        private void setComplaintMessage(String complaintMsg){
            complaintMessage.setText(complaintMsg);
        }
    }
    class MaintenanceLayout extends RecyclerView.ViewHolder{

        private TextView maintenanceMessage;
        private TextView maintenanceMessageThank;

        public MaintenanceLayout(@NonNull View itemView) {
            super(itemView);
            maintenanceMessage=itemView.findViewById(R.id.maintenance);
            maintenanceMessageThank=itemView.findViewById(R.id.maintenance_thank);
        }
        private void setMaintenanceMessage(String maintenanceMsg,String maintenanceMsgThank){
            maintenanceMessage.setText(maintenanceMsg);
            maintenanceMessageThank.setText(maintenanceMsgThank);
        }
    }
}
