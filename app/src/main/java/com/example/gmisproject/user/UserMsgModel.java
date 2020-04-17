package com.example.gmisproject.user;

public class UserMsgModel {

    public static final int INFORMATION_MESSAGE=0;
    public static final int COMPLAINT_MESSAGE=1;
    public static final int MAINTENANCE_MESSAGE=2;

    private String msg, phone, costs, complaintMsg,maintenanceMsg,maintenanceMsgThank;
    private int viewType;

    public UserMsgModel(int viewType,String msg, String phone, String costs) {
        this.msg = msg;
        this.phone = phone;
        this.costs = costs;
        this.viewType=viewType;
    }
    public UserMsgModel(int viewType,String complaintMsg) {
        this.complaintMsg = complaintMsg;
        this.viewType=viewType;
    }

    public UserMsgModel(int viewType,String maintenanceMsg, String maintenanceMsgThank) {
        this.maintenanceMsg = maintenanceMsg;
        this.maintenanceMsgThank = maintenanceMsgThank;
        this.viewType=viewType;
    }

    public UserMsgModel() {
    }

    public String getMsg() {
        return msg;
    }

    public String getPhone() {
        return phone;
    }

    public String getCosts() {
        return costs;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setCosts(String costs) {
        this.costs = costs;
    }

    public String getComplaintMsg() {
        return complaintMsg;
    }

    public void setComplaintMsg(String complaintMsg) {
        this.complaintMsg = complaintMsg;
    }

    public String getMaintenanceMsg() {
        return maintenanceMsg;
    }

    public void setMaintenanceMsg(String maintenanceMsg) {
        this.maintenanceMsg = maintenanceMsg;
    }

    public String getMaintenanceMsgThank() {
        return maintenanceMsgThank;
    }

    public void setMaintenanceMsgThank(String maintenanceMsgThank) {
        this.maintenanceMsgThank = maintenanceMsgThank;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
